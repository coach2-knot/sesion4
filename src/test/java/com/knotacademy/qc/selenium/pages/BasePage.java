package com.knotacademy.qc.selenium.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Clase base para todos los Page Objects.
 * Provee metodos utilitarios reutilizables para interactuar con elementos web.
 *
 * Conceptos clave para el estudiante:
 * - Encapsula las esperas explicitas (WebDriverWait)
 * - Centraliza acciones comunes: click, type, text, scroll, select
 * - Los Page Objects hijos solo necesitan definir localizadores y logica de negocio
 */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ==================== ESPERAS EXPLICITAS ====================

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected List<WebElement> visibleAll(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ==================== ACCIONES BASICAS ====================

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String text(By locator) {
        return visible(locator).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected int countElements(By locator) {
        return driver.findElements(locator).size();
    }

    // ==================== SELECT (DROPDOWNS) ====================

    /**
     * Selecciona una opcion de un <select> HTML por su texto visible.
     * Ejemplo: selectByVisibleText(By.id("country"), "India")
     */
    protected void selectByVisibleText(By locator, String text) {
        new Select(visible(locator)).selectByVisibleText(text);
    }

    /**
     * Selecciona una opcion de un <select> HTML por su atributo value.
     * Ejemplo: selectByValue(By.id("country"), "IN")
     */
    protected void selectByValue(By locator, String value) {
        new Select(visible(locator)).selectByValue(value);
    }

    // ==================== SCROLL ====================

    /**
     * Hace scroll hasta que el elemento sea visible en pantalla.
     * Util para paginas largas donde el elemento esta fuera del viewport.
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    /**
     * Scroll hasta el final de la pagina.
     */
    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ==================== FRAMES ====================

    protected void switchToFrame(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ==================== JAVASCRIPT ====================

    /**
     * Click via JavaScript. Util cuando un elemento esta cubierto por otro
     * (ads, overlays, banners) y el click normal falla.
     */
    protected void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ==================== NAVEGACION ====================

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }
}
