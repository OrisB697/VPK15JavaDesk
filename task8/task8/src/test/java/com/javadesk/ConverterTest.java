package com.javadesk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private final Converter converter = new Converter();

    @Test
    @DisplayName("Конвертация метров в километры")
    void MToKilom() {
        double result = converter.convert(1000.0, "m", "km");
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    @DisplayName("Конвертация километров в метры")
    void KilomToM() {
        double result = converter.convert(2.5, "km", "m");
        assertEquals(2500.0, result, 0.0001);
    }

    @Test
    @DisplayName("Проверка округления результата до 3 знаков после запятой")
    void testRounding() {
        // 1 метр в километры = 0.001 -> округление до 3 знаков
        double result = converter.convert(1.0, "m", "km");
        assertEquals(0.001, result, 0.0001);
        // 1 миля в километры (1 миля = 1.60934 км) -> ожидаем 1.609
        result = converter.convert(1.0, "mi", "km");
        assertEquals(1.609, result, 0.001);
    }

    @Test
    @DisplayName("Неподдерживаемая единица измерения – исключение")
    void testUnknownUnit() {
        assertThrows(IllegalArgumentException.class,
            () -> converter.convert(10, "unknown", "km"));
        assertThrows(IllegalArgumentException.class,
            () -> converter.convert(10, "m", "parsec"));
    }

    @Test
    @DisplayName("Некорректная конвертация (разные категории) – исключение")
    void testIncompatibleUnits() {
        assertThrows(IllegalArgumentException.class,
            () -> converter.convert(100, "kg", "m")); // масса -> длина
        assertThrows(IllegalArgumentException.class,
            () -> converter.convert(25, "C", "km")); // температура -> длина
    }
}