package com.knotacademy.qc.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPage extends BasePage {

    private final By burgerButton = By.id("react-burger-menu-btn");
    private final By menuItems = By.cssSelector(".bm-item-list a");

    public MenuPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openMenu() {
        click(burgerButton);
    }

    public boolean isMenuVisible() {
        return isVisible(menuItems);
    }

    public int countMenuOptions() {
        return countElements(menuItems);
    }
}