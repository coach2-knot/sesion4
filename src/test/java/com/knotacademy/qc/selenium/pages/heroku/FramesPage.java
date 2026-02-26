package com.knotacademy.qc.selenium.pages.heroku;

import com.knotacademy.qc.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object para la pagina de iFrames de the-internet.herokuapp.com.
 *
 * Conceptos clave para los estudiantes:
 * - switchToFrame(): cambia el contexto de Selenium al iframe
 * - switchToDefaultContent(): vuelve al documento principal
 * - Sin switchTo, Selenium NO puede ver elementos dentro del iframe
 *
 * URL: https://the-internet.herokuapp.com/iframe
 */
public class FramesPage extends BasePage {

    // Localizadores - pagina principal
    private final By iframeEditor = By.id("mce_0_ifr");
    private final By heading = By.cssSelector("h3");

    // Localizadores - DENTRO del iframe (solo visibles despues de switchToFrame)
    private final By editorBody = By.id("tinymce");
    private final By editorParagraph = By.cssSelector("#tinymce p");

    public FramesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Abre la pagina de iframes.
     */
    public void open(String baseUrl) {
        driver.get(baseUrl + "/iframe");
    }

    /**
     * Obtiene el titulo de la pagina (esta FUERA del iframe).
     */
    public String getHeading() {
        return text(heading);
    }

    /**
     * Lee el texto del editor TinyMCE que esta DENTRO del iframe.
     *
     * Flujo:
     * 1. switchToFrame - entrar al iframe
     * 2. leer el texto
     * 3. switchToDefaultContent - salir del iframe
     *
     * Si no haces el switchTo, obtienes NoSuchElementException.
     */
    public String getEditorText() {
        switchToFrame(iframeEditor);       // ENTRAR al iframe
        String content = text(editorBody);
        switchToDefaultContent();           // SALIR del iframe
        return content;
    }

    /**
     * Limpia el editor y escribe texto nuevo DENTRO del iframe.
     */
    public void typeInEditor(String newText) {
        switchToFrame(iframeEditor);        // ENTRAR
        try {
            WebElement editor = visible(editorBody);
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", editor);

            // TinyMCE usa un body contenteditable; clear() puede fallar en Chrome/Selenium.
            String selectAllShortcut = Platform.getCurrent().is(Platform.MAC)
                ? Keys.chord(Keys.COMMAND, "a")
                : Keys.chord(Keys.CONTROL, "a");

            editor.sendKeys(selectAllShortcut, Keys.BACK_SPACE);
            editor.sendKeys(newText);
        } finally {
            switchToDefaultContent();        // SALIR
        }
    }

    /**
     * Verifica si el iframe del editor esta presente en la pagina.
     */
    public boolean isEditorFramePresent() {
        return isVisible(iframeEditor);
    }
}
