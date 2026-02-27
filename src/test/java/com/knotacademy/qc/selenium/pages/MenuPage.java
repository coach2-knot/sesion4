package com.knotacademy.qc.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPage extends BasePage {

    private final By burgerButton = By.id("react-burger-menu-btn");
    private final By menuWrap = By.cssSelector(".bm-menu-wrap[aria-hidden='false']");
    private final By menuItems = By.cssSelector(".bm-item-list a");

    public MenuPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Abre el menu lateral y espera a que la animacion CSS termine.
     * SauceDemo usa react-burger-menu que anima el menu con CSS transitions.
     * En CI headless la animacion puede tardar mas de lo esperado.
     */
    public void openMenu() {
        click(burgerButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuWrap));
    }

    public boolean isMenuVisible() {
        return isVisible(menuItems);
    }

    public int countMenuOptions() {
        return visibleAll(menuItems).size();
    }
}