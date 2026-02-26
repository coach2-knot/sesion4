package com.knotacademy.qc.selenium.tests.heroku;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.heroku.HerokuLoginPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de Login en the-internet.herokuapp.com.
 *
 * Punto clave para la clase:
 * - MISMO patron POM que SauceDemo, diferente sitio
 * - Demuestra que POM es reutilizable entre aplicaciones
 * - Credenciales: tomsmith / SuperSecretPassword!
 */
@DisplayName("Heroku - Tests de Login")
public class HerokuLoginTest extends BaseTest {

    @Test
    @DisplayName("Login exitoso con credenciales validas")
    @Description("Ingresa con tomsmith/SuperSecretPassword! y verifica el mensaje de exito")
    @Severity(SeverityLevel.BLOCKER)
    void shouldLoginSuccessfully() {
        // Arrange
        HerokuLoginPage page = new HerokuLoginPage(driver, wait);
        page.open(requiredProperty("heroku.url"));

        // Act
        page.login(
            requiredProperty("heroku.login.username"),
            requiredProperty("heroku.login.password")
        );

        // Assert
        assertTrue(page.isLoggedIn(),
            "Despues de login exitoso, el boton Logout debe ser visible");
        String flash = page.getFlashMessage();
        assertTrue(flash.contains("You logged into a secure area"),
            "El mensaje flash debe confirmar login exitoso");
    }

    @Test
    @DisplayName("Login fallido con password incorrecta")
    @Description("Intenta login con password erronea y verifica mensaje de error")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowErrorForInvalidPassword() {
        // Arrange
        HerokuLoginPage page = new HerokuLoginPage(driver, wait);
        page.open(requiredProperty("heroku.url"));

        // Act
        page.login("tomsmith", "wrongpassword");

        // Assert
        String flash = page.getFlashMessage();
        assertTrue(flash.contains("Your password is invalid"),
            "Debe mostrar mensaje de password invalida");
    }

    @Test
    @DisplayName("Login fallido con usuario inexistente")
    @Description("Intenta login con usuario que no existe y verifica mensaje de error")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowErrorForInvalidUsername() {
        // Arrange
        HerokuLoginPage page = new HerokuLoginPage(driver, wait);
        page.open(requiredProperty("heroku.url"));

        // Act
        page.login("nonexistent_user", "SuperSecretPassword!");

        // Assert
        String flash = page.getFlashMessage();
        assertTrue(flash.contains("Your username is invalid"),
            "Debe mostrar mensaje de usuario invalido");
    }

    @Test
    @DisplayName("Logout despues de login exitoso")
    @Description("Hace login, luego logout, y verifica que regresa a la pagina de login")
    @Severity(SeverityLevel.NORMAL)
    void shouldLogoutSuccessfully() {
        // Arrange
        HerokuLoginPage page = new HerokuLoginPage(driver, wait);
        page.open(requiredProperty("heroku.url"));
        page.login(
            requiredProperty("heroku.login.username"),
            requiredProperty("heroku.login.password")
        );

        // Act
        page.logout();

        // Assert
        String flash = page.getFlashMessage();
        assertTrue(flash.contains("You logged out of the secure area"),
            "El mensaje flash debe confirmar logout exitoso");
    }
}
