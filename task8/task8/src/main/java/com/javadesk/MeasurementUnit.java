package com.javadesk;

import java.util.Objects;

/**
 * Представляет единицу измерения с именем, коэффициентом перевода в базовую единицу
 * и категорией (длина, масса, температура).
 */
public class MeasurementUnit {
    private final String name;          // "m", "km", "kg", "C" и т.д.
    private final double toBaseFactor;  // множитель для перевода в базовую единицу категории
    private final Category category;

    public enum Category {
        LENGTH, MASS, TEMPERATURE
    }

    // Приватный конструктор, используем статические фабрики
    private MeasurementUnit(String name, double toBaseFactor, Category category) {
        this.name = name;
        this.toBaseFactor = toBaseFactor;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getToBaseFactor() {
        return toBaseFactor;
    }

    public Category getCategory() {
        return category;
    }

    // Предопределённые единицы
    public static final MeasurementUnit METER = new MeasurementUnit("m", 1.0, Category.LENGTH);
    public static final MeasurementUnit KILOMETER = new MeasurementUnit("km", 1000.0, Category.LENGTH);
    public static final MeasurementUnit CENTIMETER = new MeasurementUnit("cm", 0.01, Category.LENGTH);
    public static final MeasurementUnit MILE = new MeasurementUnit("mi", 1609.344, Category.LENGTH);
    public static final MeasurementUnit KILOGRAM = new MeasurementUnit("kg", 1.0, Category.MASS);
    public static final MeasurementUnit GRAM = new MeasurementUnit("g", 0.001, Category.MASS);
    public static final MeasurementUnit CELSIUS = new MeasurementUnit("c", 0.0, Category.TEMPERATURE);
    public static final MeasurementUnit FAHRENHEIT = new MeasurementUnit("f", 0.0, Category.TEMPERATURE);

    // Реестр для поиска по имени
    private static final java.util.Map<String, MeasurementUnit> registry = new java.util.HashMap<>();
    static {
        registry.put("m", METER);
        registry.put("km", KILOMETER);
        registry.put("cm", CENTIMETER);
        registry.put("mi", MILE);
        registry.put("kg", KILOGRAM);
        registry.put("g", GRAM);
        registry.put("c", CELSIUS);
        registry.put("f", FAHRENHEIT);
    }

    /**
     * Возвращает единицу по её строковому имени (регистронезависимо).
     * @throws IllegalArgumentException если единица не найдена
     */
    public static MeasurementUnit fromName(String name) {
        String key = name.toLowerCase();
        MeasurementUnit unit = registry.get(key);
        if (unit == null) {
            throw new IllegalArgumentException("Неизвестная единица измерения: " + name);
        }
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementUnit that = (MeasurementUnit) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}