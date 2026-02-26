package com.knotacademy.qc.selenium.pages.heroku;

import com.knotacademy.qc.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para la pagina de Dropdown de the-internet.herokuapp.com.
 *
 * Conceptos clave para los estudiantes:
 * - Select: clase de Selenium para interactuar con <select> HTML
 * - selectByVisibleText(): selecciona por el texto que el usuario ve
 * - getFirstSelectedOption(): obtiene la opcion actualmente seleccionada
 *
 * URL: https://the-internet.herokuapp.com/dropdown
 */
public class DropdownPage extends BasePage {

    private final By dropdown = By.id("dropdown");
    private final By heading = By.cssSelector("h3");

    public DropdownPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/dropdown");
    }

    public String getHeading() {
        return text(heading);
    }

    /**
     * Selecciona una opcion del dropdown por su texto visible.
     * Ejemplo: selectOption("Option 1")
     */
    public void selectOption(String optionText) {
        selectByVisibleText(dropdown, optionText);
    }

    /**
     * Obtiene el texto de la opcion actualmente seleccionada.
     */
    public String getSelectedOption() {
        WebElement dropdownElement = visible(dropdown);
        Select select = new Select(dropdownElement);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Cuenta cuantas opciones tiene el dropdown (incluyendo la placeholder).
     */
    public int getOptionCount() {
        WebElement dropdownElement = visible(dropdown);
        Select select = new Select(dropdownElement);
        return select.getOptions().size();
    }
}
