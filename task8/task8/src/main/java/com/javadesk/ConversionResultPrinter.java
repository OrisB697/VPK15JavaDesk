package com.javadesk;

public class ConversionResultPrinter {

    public void print(double value, MeasurementUnit fromUnit, MeasurementUnit toUnit, double result) {
        // Округляем результат до 3 знаков после запятой
        String formattedResult = String.format("%.3f", result);
        System.out.printf("%.2f %s = %s %s%n",
                value,
                fromUnit.getName(),
                formattedResult,
                toUnit.getName());
    }

    public void printError(String message) {
        System.out.println("Ошибка: " + message);
    }
}