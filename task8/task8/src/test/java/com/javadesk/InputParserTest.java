package com.javadesk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    private final InputParser parser = new InputParser();

    @Test
    @DisplayName("Корректный ввод: '10 m to km'")
    void testValidInput() {
        Object[] result = parser.parse("10 m to km");
        assertEquals(10.0, (Double) result[0]);
        assertEquals(MeasurementUnit.METER, result[1]);
        assertEquals(MeasurementUnit.KILOMETER, result[2]);
    }

    @Test
    @DisplayName("Ввод с лишними пробелами и разными регистрами")
    void testInputWithSpacesAndCase() {
        Object[] result = parser.parse("  100.5  KG   to   g  ");
        assertEquals(100.5, (Double) result[0]);
        assertEquals(MeasurementUnit.KILOGRAM, result[1]);
        assertEquals(MeasurementUnit.GRAM, result[2]);
    }

    @Test
    @DisplayName("Некорректный формат – отсутствует 'to'")
    void testMissingToKeyword() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("10 m km"));
    }

    @Test
    @DisplayName("Пустая строка или null")
    void testNullOrEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null));
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("   "));
    }

    @Test
    @DisplayName("Невозможно преобразовать значение в число")
    void testInvalidNumber() {
        assertThrows(NumberFormatException.class,
                () -> parser.parse("abc m to km"));
    }

    @Test
    @DisplayName("Неизвестная единица измерения")
    void testUnknownUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("100 foo to bar"));
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("10 m to parsec"));
    }

    @Test
    @DisplayName("Смешанный регистр единиц")
    void testCaseInsensitiveUnits() {
        Object[] result = parser.parse("1 Km To M");
        assertEquals(MeasurementUnit.KILOMETER, result[1]);
        assertEquals(MeasurementUnit.METER, result[2]);
    }

}