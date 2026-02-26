package com.knotacademy.qc.selenium.core;

import com.knotacademy.qc.selenium.config.ConfigLoader;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        String browser = resolveProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(resolveProperty("headless", "false"));
        long timeoutSeconds = Long.parseLong(resolveProperty("timeout.seconds", "10"));

        driver = DriverFactory.createDriver(browser, headless);
        if (!headless) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    @AfterEach
    void tearDown() {
        boolean keepOpen = Boolean.parseBoolean(System.getProperty("keepBrowserOpen", "false"));
        if (!keepOpen && driver != null) {
            driver.quit();
        }
    }

    protected String requiredProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return ConfigLoader.get(key);
    }

    protected String resolveProperty(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return ConfigLoader.getOrDefault(key, defaultValue);
    }
}
