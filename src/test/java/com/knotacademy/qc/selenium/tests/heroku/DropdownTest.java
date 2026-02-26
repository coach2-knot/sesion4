package com.knotacademy.qc.selenium.tests.heroku;

import com.knotacademy.qc.selenium.core.BaseTest;
import com.knotacademy.qc.selenium.pages.heroku.DropdownPage;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de Dropdowns (Select) - the-internet.herokuapp.com
 *
 * Demuestra:
 * - Clase Select de Selenium para interactuar con <select>
 * - selectByVisibleText()
 * - getFirstSelectedOption()
 */
@DisplayName("Heroku - Tests de Dropdown")
public class DropdownTest extends BaseTest {

    private DropdownPage dropdownPage;

    @BeforeEach
    void navigateToDropdown() {
        dropdownPage = new DropdownPage(driver, wait);
        dropdownPage.open(requiredProperty("heroku.url"));
    }

    @Test
    @DisplayName("La pagina de dropdown carga correctamente")
    @Description("Verifica que el heading es 'Dropdown List' y el dropdown tiene 3 opciones")
    @Severity(SeverityLevel.NORMAL)
    void shouldLoadDropdownPage() {
        assertEquals("Dropdown List", dropdownPage.getHeading(),
            "El heading debe ser 'Dropdown List'");

        int options = dropdownPage.getOptionCount();
        assertEquals(3, options,
            "El dropdown debe tener 3 opciones (placeholder + Option 1 + Option 2)");
    }

    @Test
    @DisplayName("Seleccionar Option 1 del dropdown")
    @Description("Selecciona 'Option 1' y verifica que queda seleccionada")
    @Severity(SeverityLevel.CRITICAL)
    void shouldSelectOption1() {
        // Act
        dropdownPage.selectOption("Option 1");

        // Assert
        String selected = dropdownPage.getSelectedOption();
        assertEquals("Option 1", selected,
            "La opcion seleccionada debe ser 'Option 1'");
    }

    @Test
    @DisplayName("Cambiar seleccion de Option 1 a Option 2")
    @Description("Selecciona Option 1, luego cambia a Option 2 y verifica")
    @Severity(SeverityLevel.NORMAL)
    void shouldChangeSelection() {
        // Act
        dropdownPage.selectOption("Option 1");
        assertEquals("Option 1", dropdownPage.getSelectedOption());

        dropdownPage.selectOption("Option 2");

        // Assert
        assertEquals("Option 2", dropdownPage.getSelectedOption(),
            "Despues de cambiar, la opcion seleccionada debe ser 'Option 2'");
    }
}
