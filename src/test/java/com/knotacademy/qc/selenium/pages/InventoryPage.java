package com.knotacademy.qc.selenium.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage extends BasePage {
    private final By pageTitle = By.className("title");
    private final By addBackpackButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private final By addBikeLightButton = By.id("add-to-cart-sauce-labs-bike-light");
    private final By removeBikeLightButton = By.id("remove-sauce-labs-bike-light");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Espera a que la pagina de inventario cargue completamente.
     * Llamar despues del login para asegurar que React monto los event listeners.
     */
    public void waitForPageLoad() {
        visible(pageTitle);
    }

    public boolean isLoaded() {
        return "Products".equals(text(pageTitle));
    }

    /**
     * Agrega Backpack al carrito.
     * Usa JavaScript click porque en Chrome 145 headless (GitHub Actions)
     * el click nativo de Selenium no siempre dispara el event handler de React.
     * Espera a que el boton cambie de "Add to cart" a "Remove" como confirmacion.
     */
    public void addBackpack() {
        jsClick(addBackpackButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeBackpackButton));
    }

    /**
     * Agrega Bike Light al carrito.
     */
    public void addBikeLight() {
        jsClick(addBikeLightButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeBikeLightButton));
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }

        String value = badges.get(0).getText();
        if (value == null || value.isBlank()) {
            return 0;
        }

        return Integer.parseInt(value.trim());
    }

    public void openCart() {
        click(cartLink);
    }

    /** Alias for openCart — uses business-friendly name */
    public void goToCart() {
        click(cartLink);
    }
}
