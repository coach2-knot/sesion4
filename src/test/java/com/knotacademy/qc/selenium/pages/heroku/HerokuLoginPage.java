package com.knotacademy.qc.selenium.pages.heroku;

import com.knotacademy.qc.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para la pagina de Login de the-internet.herokuapp.com.
 *
 * Diferencia con SauceDemo LoginPage:
 * - Diferente sitio, diferentes selectores, pero MISMA estructura POM
 * - Demuestra que POM se adapta a cualquier aplicacion
 *
 * URL: https://the-internet.herokuapp.com/login
 * Credenciales: tomsmith / SuperSecretPassword!
 */
public class HerokuLoginPage extends BasePage {

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");
    private final By logoutButton = By.cssSelector("a[href='/logout']");

    public HerokuLoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/login");
    }

    /**
     * Realiza login con las credenciales proporcionadas.
     * Misma estructura que SauceDemo LoginPage: type + type + click.
     */
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    /**
     * Obtiene el mensaje flash (exito o error) despues del login.
     */
    public String getFlashMessage() {
        return text(flashMessage).trim();
    }

    /**
     * Verifica si el boton de logout esta visible (= login exitoso).
     */
    public boolean isLoggedIn() {
        return isVisible(logoutButton);
    }

    /**
     * Hace logout.
     */
    public void logout() {
        click(logoutButton);
    }
}
