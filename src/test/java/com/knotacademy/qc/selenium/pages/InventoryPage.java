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
    private final By addBikeLightButton = By.id("add-to-cart-sauce-labs-bike-light");
    private final By cartBadge = By.cssSelector(".shopping_cart_link .shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isLoaded() {
        return "Products".equals(text(pageTitle));
    }

    public void addBackpack() {
        int currentCount = getCartBadgeCount();
        click(addBackpackButton);
        waitForCartBadge(currentCount + 1);
    }

    public void addBikeLight() {
        int currentCount = getCartBadgeCount();
        click(addBikeLightButton);
        waitForCartBadge(currentCount + 1);
    }

    /**
     * Espera a que el badge del carrito aparezca y muestre el valor esperado.
     * El badge NO existe en el DOM cuando el carrito esta vacio,
     * por eso primero esperamos visibilidad y luego el texto.
     */
    private void waitForCartBadge(int expectedCount) {
        String expected = String.valueOf(expectedCount);
        wait.until(d -> {
            List<WebElement> badges = d.findElements(cartBadge);
            if (badges.isEmpty()) return false;
            String text = badges.get(0).getText();
            return expected.equals(text != null ? text.trim() : "");
        });
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
