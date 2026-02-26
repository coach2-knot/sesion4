package com.knotacademy.qc.selenium.tests.heroku;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.heroku.FramesPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de iFrames - the-internet.herokuapp.com
 *
 * Demuestra:
 * - switchToFrame / switchToDefaultContent
 * - Leer contenido dentro de un iframe
 * - Escribir dentro de un iframe
 * - El error clasico de no hacer switchTo
 */
@DisplayName("Heroku - Tests de iFrames")
public class FramesTest extends BaseTest {

    private FramesPage framesPage;

    @BeforeEach
    void navigateToFrames() {
        framesPage = new FramesPage(driver, wait);
        framesPage.open(requiredProperty("heroku.url"));
    }

    @Test
    @DisplayName("La pagina de iframes carga correctamente")
    @Description("Verifica que el heading y el iframe del editor estan presentes")
    @Severity(SeverityLevel.NORMAL)
    void shouldLoadFramesPage() {
        // Assert
        String heading = framesPage.getHeading();
        assertTrue(heading.contains("Editor"),
            "El heading debe contener 'Editor'. Actual: " + heading);
        assertTrue(framesPage.isEditorFramePresent(),
            "El iframe del editor TinyMCE debe estar presente");
    }

    @Test
    @DisplayName("Leer texto dentro del iframe del editor")
    @Description("Entra al iframe, lee el contenido del editor TinyMCE y sale al documento principal")
    @Severity(SeverityLevel.CRITICAL)
    void shouldReadTextInsideIframe() {
        // Act
        String editorText = framesPage.getEditorText();

        // Assert - TinyMCE tiene texto por defecto "Your content goes here."
        assertNotNull(editorText, "El texto del editor no debe ser null");
        assertFalse(editorText.isBlank(),
            "El editor debe tener contenido por defecto");
    }

    @Test
    @DisplayName("Escribir texto dentro del iframe del editor")
    @Description("Limpia el editor, escribe texto nuevo y verifica que se guardo")
    @Severity(SeverityLevel.CRITICAL)
    void shouldTypeTextInsideIframe() {
        // Arrange
        String textoNuevo = "Hola desde Selenium dentro del iframe!";

        // Act
        framesPage.typeInEditor(textoNuevo);
        String resultado = framesPage.getEditorText();

        // Assert
        assertEquals(textoNuevo, resultado,
            "El texto en el editor debe ser el que escribimos");
    }

    @Test
    @DisplayName("Despues de interactuar con el iframe, se puede leer el heading principal")
    @Description("Verifica que switchToDefaultContent funciona: despues de entrar/salir del iframe, " +
                 "los elementos de la pagina principal siguen accesibles")
    @Severity(SeverityLevel.NORMAL)
    void shouldAccessMainPageAfterIframeInteraction() {
        // Act - primero interactuar dentro del iframe
        framesPage.typeInEditor("Test de contexto");

        // Assert - luego verificar que se puede leer fuera del iframe
        String heading = framesPage.getHeading();
        assertTrue(heading.contains("Editor"),
            "Despues de salir del iframe, el heading principal debe seguir accesible");
    }
}
