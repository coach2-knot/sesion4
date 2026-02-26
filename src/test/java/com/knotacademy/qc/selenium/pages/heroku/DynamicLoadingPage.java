package com.knotacademy.qc.selenium.pages.heroku;

import com.knotacademy.qc.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para Dynamic Loading de the-internet.herokuapp.com.
 *
 * Conceptos clave para los estudiantes:
 * - Elementos que NO existen en el DOM hasta que se ejecuta una accion
 * - Esperas explicitas (WebDriverWait) son OBLIGATORIAS aqui
 * - Sin wait, el test falla con NoSuchElementException
 * - invisibilityOfElementLocated(): espera que el loading desaparezca
 *
 * URL: https://the-internet.herokuapp.com/dynamic_loading/1
 *   Ejemplo 1: El elemento esta oculto, se revela al hacer click
 * URL: https://the-internet.herokuapp.com/dynamic_loading/2
 *   Ejemplo 2: El elemento NO existe en el DOM, se crea al hacer click
 */
public class DynamicLoadingPage extends BasePage {

    private final By startButton = By.cssSelector("#start button");
    private final By loadingBar = By.id("loading");
    private final By finishText = By.cssSelector("#finish h4");
    private final By heading = By.cssSelector("h3");

    public DynamicLoadingPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Abre el ejemplo 1 (elemento oculto que se revela).
     */
    public void openExample1(String baseUrl) {
        driver.get(baseUrl + "/dynamic_loading/1");
    }

    /**
     * Abre el ejemplo 2 (elemento que se crea dinamicamente).
     */
    public void openExample2(String baseUrl) {
        driver.get(baseUrl + "/dynamic_loading/2");
    }

    public String getHeading() {
        return text(heading);
    }

    /**
     * Hace click en Start y espera a que el loading termine.
     *
     * Flujo interno:
     * 1. Click en "Start"
     * 2. Aparece barra de loading
     * 3. Espera hasta que la barra DESAPAREZCA (invisibilityOfElementLocated)
     * 4. El texto "Hello World!" aparece
     */
    public void clickStartAndWaitForLoading() {
        click(startButton);
        // Esperar a que el loading desaparezca
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingBar));
    }

    /**
     * Obtiene el texto que aparece despues del loading.
     */
    public String getFinishText() {
        return text(finishText);
    }

    /**
     * Verifica si el boton Start es visible.
     */
    public boolean isStartButtonVisible() {
        return isVisible(startButton);
    }
}
