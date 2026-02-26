package com.knotacademy.qc.selenium.tests.heroku;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.heroku.DynamicLoadingPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de Dynamic Loading - the-internet.herokuapp.com
 *
 * Demuestra:
 * - Elementos que aparecen DESPUES de una accion (carga asincrona)
 * - Esperas explicitas: invisibilityOfElementLocated para el spinner
 * - visibilityOfElementLocated para el resultado
 * - Sin waits, estos tests SIEMPRE fallan
 */
@DisplayName("Heroku - Tests de Dynamic Loading")
public class DynamicLoadingTest extends BaseTest {

    @Test
    @DisplayName("Ejemplo 1: Elemento oculto que se revela")
    @Description("El elemento ya existe en el DOM pero esta oculto. " +
                 "Al hacer click en Start, se muestra despues de un loading.")
    @Severity(SeverityLevel.CRITICAL)
    void shouldRevealHiddenElement() {
        // Arrange
        DynamicLoadingPage page = new DynamicLoadingPage(driver, wait);
        page.openExample1(requiredProperty("heroku.url"));

        // Act
        page.clickStartAndWaitForLoading();

        // Assert
        String finishText = page.getFinishText();
        assertEquals("Hello World!", finishText,
            "Despues del loading, debe aparecer 'Hello World!'");
    }

    @Test
    @DisplayName("Ejemplo 2: Elemento que se crea dinamicamente")
    @Description("El elemento NO existe en el DOM hasta que se completa el loading. " +
                 "Demuestra por que las esperas explicitas son esenciales.")
    @Severity(SeverityLevel.CRITICAL)
    void shouldRenderDynamicElement() {
        // Arrange
        DynamicLoadingPage page = new DynamicLoadingPage(driver, wait);
        page.openExample2(requiredProperty("heroku.url"));

        // Act
        page.clickStartAndWaitForLoading();

        // Assert
        String finishText = page.getFinishText();
        assertEquals("Hello World!", finishText,
            "Despues del loading, el elemento creado dinamicamente debe decir 'Hello World!'");
    }

    @Test
    @DisplayName("Verificar que el boton Start es visible antes de hacer click")
    @Description("Sanity check: el boton Start debe estar visible al cargar la pagina")
    @Severity(SeverityLevel.MINOR)
    void shouldShowStartButton() {
        // Arrange
        DynamicLoadingPage page = new DynamicLoadingPage(driver, wait);
        page.openExample1(requiredProperty("heroku.url"));

        // Assert
        assertTrue(page.isStartButtonVisible(),
            "El boton Start debe ser visible al cargar la pagina");
    }
}
