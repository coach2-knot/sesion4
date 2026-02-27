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
    @DisplayName("Agregar Backpack al carrito")
    @Description("Verifica que al agregar Sauce Labs Backpack, el badge del carrito muestra 1")
    @Severity(SeverityLevel.CRITICAL)
    void shouldAddBackpackToCart() {
        // Act
        inventoryPage.addBackpack();

        // Assert
        int cartCount = inventoryPage.getCartBadgeCount();
        assertEquals(1, cartCount,
            "El badge del carrito debe mostrar 1 despues de agregar Backpack");
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

    @Test
    @DisplayName("Verificar producto en pagina del carrito")
    @Description("Agrega Backpack, navega al carrito y verifica que el producto aparece")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowProductInCartPage() {
        // Act
        inventoryPage.addBackpack();
        inventoryPage.goToCart();

        // Assert
        int itemCount = cartPage.getItemCount();
        assertTrue(itemCount > 0,
            "El carrito debe tener al menos 1 item despues de agregar Backpack");
    }
}
