package com.javadesk;

public class InputParser {

    /**
     * Разбирает строку ввода.
     * @return Object[]{значение (Double), fromUnit (MeasurementUnit), toUnit (MeasurementUnit)}
     */
    public Object[] parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Ввод не может быть пустым");
        }

        String trimmed = input.trim().toLowerCase();
        int toIndex = trimmed.indexOf(" to ");
        if (toIndex == -1) {
            throw new IllegalArgumentException("Отсутствует ключевое слово 'to'");
        }

        String leftPart = trimmed.substring(0, toIndex).trim();
        String rightPart = trimmed.substring(toIndex + 4).trim();

        if (leftPart.isEmpty() || rightPart.isEmpty()) {
            throw new IllegalArgumentException("Неверный формат");
        }

        int lastSpace = leftPart.lastIndexOf(' ');
        if (lastSpace == -1) {
            throw new IllegalArgumentException("Не удалось отделить число от единицы");
        }

        String valueStr = leftPart.substring(0, lastSpace).trim();
        String fromUnitName = leftPart.substring(lastSpace + 1).trim();
        String toUnitName = rightPart;

        if (valueStr.isEmpty() || fromUnitName.isEmpty() || toUnitName.isEmpty()) {
            throw new IllegalArgumentException("Число или единицы отсутствуют");
        }

        double value;
        try {
            value = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Не число: " + valueStr);
        }

        MeasurementUnit fromUnit = MeasurementUnit.fromName(fromUnitName);
        MeasurementUnit toUnit = MeasurementUnit.fromName(toUnitName);

        return new Object[]{value, fromUnit, toUnit};
    }
}