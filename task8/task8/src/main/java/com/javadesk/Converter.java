package com.javadesk;

public class Converter {

    /**
     * Конвертирует значение из одной единицы в другую.
     */
    public double convert(double value, MeasurementUnit from, MeasurementUnit to) {
        if (from.getCategory() != to.getCategory()) {
            throw new IllegalArgumentException("Несовместимые категории: " +
                    from.getCategory() + " -> " + to.getCategory());
        }

        // Для температуры – особая формула, для остальных – линейное масштабирование
        if (from.getCategory() == MeasurementUnit.Category.TEMPERATURE) {
            return convertTemperature(value, from, to);
        } else {
            // Линейная конвертация через базовую единицу (например, метр или килограмм)
            double valueInBase = value * from.getToBaseFactor();
            return valueInBase / to.getToBaseFactor();
        }
    }

    private double convertTemperature(double value, MeasurementUnit from, MeasurementUnit to) {
        // Приводим к Цельсию
        double celsius;
        if (from.equals(MeasurementUnit.CELSIUS)) {
            celsius = value;
        } else if (from.equals(MeasurementUnit.FAHRENHEIT)) {
            celsius = (value - 32) * 5 / 9;
        } else {
            throw new IllegalArgumentException("Неподдерживаемая температура: " + from.getName());
        }

        // Из Цельсия в нужную единицу
        if (to.equals(MeasurementUnit.CELSIUS)) {
            return celsius;
        } else if (to.equals(MeasurementUnit.FAHRENHEIT)) {
            return celsius * 9 / 5 + 32;
        } else {
            throw new IllegalArgumentException("Неподдерживаемая температура: " + to.getName());
        }
    }

    public double convert(double value, String fromUnit, String toUnit) {
        return convert(value,
                MeasurementUnit.fromName(fromUnit),
                MeasurementUnit.fromName(toUnit));
    }
}