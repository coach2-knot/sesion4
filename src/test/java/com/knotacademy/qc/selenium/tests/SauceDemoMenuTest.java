package com.knotacademy.qc.selenium.tests;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.InventoryPage;
import com.knotacademy.qc.selenium.pages.LoginPage;
import com.knotacademy.qc.selenium.pages.MenuPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests del menú lateral en SauceDemo.
 *
 * Demuestra:
 * - Abrir el menú lateral
 * - Verificar visibilidad de todas las opciones
 */
@DisplayName("SauceDemo - Tests de Menú")
public class SauceDemoMenuTest extends BaseTest {

    private LoginPage loginPage;
    private MenuPage menuPage;

    @BeforeEach
    void loginAndNavigate() {
        loginPage = new LoginPage(driver, wait);
        menuPage = new MenuPage(driver, wait);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);

        driver.get(requiredProperty("base.url"));
        loginPage.login(
                requiredProperty("username"),
                requiredProperty("password")
        );
        inventoryPage.waitForPageLoad();
    }

    @Test
    @DisplayName("Abrir menú lateral y verificar opciones")
    @Description("Hace click en el botón 'Open Menu' y verifica que las opciones del menú sean visibles")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowAllMenuOptions() {
        // Act
        menuPage.openMenu();

        // Assert
        assertTrue(menuPage.isMenuVisible(), "El menú debería ser visible");
        assertEquals(4, menuPage.countMenuOptions(), "Debe haber 4 opciones en el menú");
    }
}