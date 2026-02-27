package com.knotacademy.qc.selenium.tests;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.LoginPage;
import com.knotacademy.qc.selenium.pages.InventoryPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de login en SauceDemo.
 * Estos tests estan listos para ejecutarse en CI/CD con GitHub Actions.
 *
 * Puntos clave para los estudiantes:
 * - @DisplayName: nombre legible del test en los reportes
 * - @Description (Allure): aparece en el dashboard de reportes
 * - @Severity (Allure): clasifica la criticidad del test
 * - assertTrue/assertEquals: verificaciones que determinan si el test pasa o falla
 */
@DisplayName("SauceDemo - Tests de Login")
public class SauceDemoLoginTest extends BaseTest {

    @Test
    @DisplayName("Login exitoso con usuario standard")
    @Description("Verifica que un usuario valido puede iniciar sesion y llegar al inventario")
    @Severity(SeverityLevel.CRITICAL)
    void shouldLoginSuccessfullyWithStandardUser() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        // Act
        driver.get(requiredProperty("base.url"));
        loginPage.login(
            requiredProperty("username"),
            requiredProperty("password")
        );

        // Assert
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("inventory"),
            "Despues del login, la URL debe contener 'inventory'. URL actual: " + currentUrl);
    }

    @Test
    @DisplayName("Login fallido con credenciales incorrectas")
    @Description("Verifica que se muestra error al intentar login con password incorrecto")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowErrorWithInvalidCredentials() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);

        // Act
        driver.get(requiredProperty("base.url"));
        loginPage.login("standard_user", "wrong_password");

        // Assert
        String errorMessage = loginPage.getErrorMessage();
        assertFalse(errorMessage.isEmpty(),
            "Debe mostrar un mensaje de error con credenciales incorrectas");
        assertTrue(errorMessage.contains("do not match"),
            "El mensaje debe indicar que las credenciales no coinciden");
    }

    @Test
    @DisplayName("Login bloqueado con usuario locked_out")
    @Description("Verifica que el usuario bloqueado recibe mensaje especifico de bloqueo")
    @Severity(SeverityLevel.NORMAL)
    void shouldShowLockedOutMessageForBlockedUser() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver, wait);

        // Act
        driver.get(requiredProperty("base.url"));
        loginPage.login("locked_out_user", "secret_sauce");

        // Assert
        String errorMessage = loginPage.getErrorMessage();
        assertTrue(errorMessage.contains("locked out"),
            "Debe indicar que el usuario esta bloqueado");
    }

    @Test
void loginSencillo() {
    // 1. Instanciar la página que acabas de crear
    LoginPage login = new LoginPage(driver, wait);
    
    // 2. Usar sus métodos
    login.open("https://www.saucedemo.com/");
    login.login("standard_user", "secret_sauce");
    
    // 3. Verificar que entramos (la URL cambia)
    assertTrue(driver.getCurrentUrl().contains("inventory"));
}
}
