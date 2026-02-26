package com.knotacademy.qc.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
    private final By pageTitle = By.className("title");
    private final By cartItems = By.className("cart_item");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isLoaded() {
        return "Your Cart".equals(text(pageTitle));
    }

    public int getItemsCount() {
        return driver.findElements(cartItems).size();
    }

    /** Alias — business-friendly name */
    public int getItemCount() {
        return getItemsCount();
    }
}
