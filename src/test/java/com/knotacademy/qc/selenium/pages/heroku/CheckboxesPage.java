package com.knotacademy.qc.selenium.pages.heroku;

import com.knotacademy.qc.selenium.pages.BasePage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para la pagina de Checkboxes de the-internet.herokuapp.com.
 *
 * Conceptos clave para los estudiantes:
 * - isSelected(): verifica si un checkbox esta marcado
 * - click() en un checkbox lo marca/desmarca (toggle)
 * - findElements() retorna lista - util cuando hay varios elementos iguales
 *
 * URL: https://the-internet.herokuapp.com/checkboxes
 */
public class CheckboxesPage extends BasePage {

    private final By checkboxes = By.cssSelector("#checkboxes input[type='checkbox']");
    private final By heading = By.cssSelector("h3");

    public CheckboxesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/checkboxes");
    }

    public String getHeading() {
        return text(heading);
    }

    /**
     * Obtiene la cantidad de checkboxes en la pagina.
     */
    public int getCheckboxCount() {
        return driver.findElements(checkboxes).size();
    }

    /**
     * Verifica si el checkbox en la posicion indicada esta marcado.
     * @param index 0 = primer checkbox, 1 = segundo, etc.
     */
    public boolean isChecked(int index) {
        List<WebElement> boxes = driver.findElements(checkboxes);
        if (index >= boxes.size()) {
            throw new IndexOutOfBoundsException(
                "Solo hay " + boxes.size() + " checkboxes, pediste indice " + index);
        }
        return boxes.get(index).isSelected();
    }

    /**
     * Hace click en el checkbox de la posicion indicada (lo marca o desmarca).
     */
    public void toggleCheckbox(int index) {
        List<WebElement> boxes = driver.findElements(checkboxes);
        if (index >= boxes.size()) {
            throw new IndexOutOfBoundsException(
                "Solo hay " + boxes.size() + " checkboxes, pediste indice " + index);
        }
        boxes.get(index).click();
    }

    /**
     * Marca el checkbox si no esta marcado. No hace nada si ya esta marcado.
     */
    public void check(int index) {
        if (!isChecked(index)) {
            toggleCheckbox(index);
        }
    }

    /**
     * Desmarca el checkbox si esta marcado. No hace nada si ya esta desmarcado.
     */
    public void uncheck(int index) {
        if (isChecked(index)) {
            toggleCheckbox(index);
        }
    }
}
