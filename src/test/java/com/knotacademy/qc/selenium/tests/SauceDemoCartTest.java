package com.knotacademy.qc.selenium.tests;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.LoginPage;
import com.knotacademy.qc.selenium.pages.InventoryPage;
import com.knotacademy.qc.selenium.pages.CartPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests del carrito de compras en SauceDemo.
 *
 * Estos tests demuestran:
 * - Setup compartido con @BeforeEach (login automatico)
 * - Flujo end-to-end: login -> agregar producto -> verificar carrito
 * - Anotaciones de Allure para reportes bonitos
 */
@DisplayName("SauceDemo - Tests de Carrito")
public class SauceDemoCartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeEach
    void loginAndNavigate() {
        loginPage = new LoginPage(driver, wait);
        inventoryPage = new InventoryPage(driver, wait);
        cartPage = new CartPage(driver, wait);

        driver.get(requiredProperty("base.url"));
        loginPage.login(
            requiredProperty("username"),
            requiredProperty("password")
        );
    }

    @Test
    @DisplayName("Agregar dos productos al carrito")
    @Description("Verifica que se pueden agregar multiples productos y el badge refleja la cantidad")
    @Severity(SeverityLevel.NORMAL)
    void shouldAddTwoProductsToCart() {
        // Act
        inventoryPage.addBackpack();
        inventoryPage.addBikeLight();

        // Assert
        int cartCount = inventoryPage.getCartBadgeCount();
        assertEquals(2, cartCount,
            "El badge del carrito debe mostrar 2 despues de agregar dos productos");
    }
}
