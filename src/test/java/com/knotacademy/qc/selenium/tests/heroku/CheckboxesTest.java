package com.knotacademy.qc.selenium.tests.heroku;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.heroku.CheckboxesPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de Checkboxes - the-internet.herokuapp.com
 *
 * Demuestra:
 * - isSelected() para verificar estado de checkbox
 * - click() para marcar/desmarcar (toggle)
 * - findElements() para trabajar con multiples elementos similares
 */
@DisplayName("Heroku - Tests de Checkboxes")
public class CheckboxesTest extends BaseTest {

    private CheckboxesPage checkboxesPage;

    @BeforeEach
    void navigateToCheckboxes() {
        checkboxesPage = new CheckboxesPage(driver, wait);
        checkboxesPage.open(requiredProperty("heroku.url"));
    }

    @Test
    @DisplayName("La pagina tiene 2 checkboxes")
    @Description("Verifica que la pagina de checkboxes carga con exactamente 2 checkboxes")
    @Severity(SeverityLevel.NORMAL)
    void shouldHaveTwoCheckboxes() {
        assertEquals(2, checkboxesPage.getCheckboxCount(),
            "Debe haber exactamente 2 checkboxes");
    }

    @Test
    @DisplayName("Estado inicial: checkbox 1 desmarcado, checkbox 2 marcado")
    @Description("Verifica el estado por defecto de los checkboxes al cargar la pagina")
    @Severity(SeverityLevel.NORMAL)
    void shouldHaveCorrectDefaultState() {
        // El primer checkbox (indice 0) esta desmarcado por defecto
        assertFalse(checkboxesPage.isChecked(0),
            "Checkbox 1 debe estar desmarcado por defecto");

        // El segundo checkbox (indice 1) esta marcado por defecto
        assertTrue(checkboxesPage.isChecked(1),
            "Checkbox 2 debe estar marcado por defecto");
    }

    @Test
    @DisplayName("Marcar checkbox 1 y verificar")
    @Description("Marca el primer checkbox y verifica que su estado cambia a true")
    @Severity(SeverityLevel.CRITICAL)
    void shouldCheckFirstCheckbox() {
        // Act
        checkboxesPage.check(0);

        // Assert
        assertTrue(checkboxesPage.isChecked(0),
            "Checkbox 1 debe estar marcado despues de hacer check");
    }

    @Test
    @DisplayName("Desmarcar checkbox 2 y verificar")
    @Description("Desmarca el segundo checkbox y verifica que su estado cambia a false")
    @Severity(SeverityLevel.CRITICAL)
    void shouldUncheckSecondCheckbox() {
        // Act
        checkboxesPage.uncheck(1);

        // Assert
        assertFalse(checkboxesPage.isChecked(1),
            "Checkbox 2 debe estar desmarcado despues de hacer uncheck");
    }
}
