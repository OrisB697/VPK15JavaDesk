package com.javadesk.task5;

import com.javadesk.fraction.*;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.io.IOException;

public class App {
    
    private static Screen screen;
    private static TextGraphics tg;
    private static TerminalSize size;
    
    // Текущая операция
    private enum Mode {
        MAIN_MENU,       // главное меню
        INPUT_FRACTION1, // ввод первой дроби
        INPUT_FRACTION2, // ввод второй дроби
        SHOW_RESULT      // показ результата
    }
    
    private static Mode mode = Mode.MAIN_MENU;
    
    // Выбранный пункт меню
    private static int menuIndex = 0;
    private static final String[] menuItems = {
        "1. Сложить дроби",
        "2. Вычесть дроби", 
        "3. Умножить дроби",
        "4. Разделить дроби",
        "5. Сравнить дроби",
        "6. Выход"
    };
    
    // Храним дроби
    private static Fraction fraction1;
    private static Fraction fraction2;
    private static Fraction result;
    
    // Ввод чисел
    private static StringBuilder inputBuffer = new StringBuilder();
    private static int inputStep = 0; // 0 - числитель 1, 1 - знаменатель 1, 2 - числитель 2, 3 - знаменатель 2
    private static int[] inputValues = new int[4];
    
    // Сообщение об ошибке
    private static String errorMessage = "";
    
    public static void main(String[] args) {
        try {
            // Создаём терминал
            Terminal terminal = new DefaultTerminalFactory()
            .setInitialTerminalSize(new TerminalSize(70, 20))
            .setTerminalEmulatorFontConfiguration(
                new SwingTerminalFontConfiguration(
                    true,
                    SwingTerminalFontConfiguration.BoldMode.NOTHING,
                    new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 22) 
                )
            )
            .createTerminal();
            
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null); // скрываем курсор по умолчанию
            
            tg = screen.newTextGraphics();
            size = screen.getTerminalSize();
            
            // Главный цикл
            while (true) {
                draw();
                screen.refresh();
                
                KeyStroke key = screen.readInput();
                
                if (mode == Mode.MAIN_MENU) {
                    handleMainMenu(key);
                } else if (mode == Mode.INPUT_FRACTION1 || mode == Mode.INPUT_FRACTION2) {
                    handleInput(key);
                } else if (mode == Mode.SHOW_RESULT) {
                    handleResult(key);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Отрисовка экрана в зависимости от режима
     */
    private static void draw() {
        tg.fillRectangle(
            com.googlecode.lanterna.TerminalPosition.TOP_LEFT_CORNER, 
            size, ' ');
        
        drawTitle();
        
        switch (mode) {
            case MAIN_MENU:
                drawMainMenu();
                break;
            case INPUT_FRACTION1:
                drawInputScreen("ПЕРВАЯ ДРОБЬ");
                break;
            case INPUT_FRACTION2:
                drawInputScreen("ВТОРАЯ ДРОБЬ");
                break;
            case SHOW_RESULT:
                drawResult();
                break;
        }
        
        if (!errorMessage.isEmpty()) {
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(2, size.getRows() - 2, "Ошибка: " + errorMessage);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        }
    }
    
    /**
     * Рисует заголовок
     */
    private static void drawTitle() {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        
        String title = "КАЛЬКУЛЯТОР ДРОБЕЙ";
        int x = (size.getColumns() - title.length()) / 2;
        tg.putString(x, 1, title, com.googlecode.lanterna.SGR.BOLD);
        
        // Линия под заголовком
        tg.drawLine(2, 2, size.getColumns() - 3, 2, '─');
        
        tg.setForegroundColor(TextColor.ANSI.WHITE);
    }
    
    /**
     * Рисует главное меню
     */
    private static void drawMainMenu() {
        int startY = 4;
        
        tg.putString(2, startY, "Выберите операцию (стрелки ↑↓, Enter для подтверждения):");
        
        for (int i = 0; i < menuItems.length; i++) {
            int y = startY + 2 + i;
            
            if (i == menuIndex) {
                // Подсвеченный пункт
                tg.setForegroundColor(TextColor.ANSI.BLACK);
                tg.setBackgroundColor(TextColor.ANSI.WHITE);
                tg.putString(4, y, " " + menuItems[i]);
                for (int x = menuItems[i].length() + 5; x < 50; x++) {
                    tg.putString(x, y, " ");
                }
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.setBackgroundColor(TextColor.ANSI.BLACK);
            } else {
                tg.putString(4, y, "  " + menuItems[i]);
            }
        }
    }
    
    /**
     * Рисует экран ввода дроби
     */
    private static void drawInputScreen(String fractionName) {
        int startY = 4;
        
        tg.putString(2, startY, "Ввод " + fractionName);
        tg.putString(2, startY + 1, "─────────────────────────");
        
        String[] labels = {"Числитель", "Знаменатель"};
        int baseStep = (mode == Mode.INPUT_FRACTION1) ? 0 : 2;
        
        for (int i = 0; i < 2; i++) {
            int step = baseStep + i;
            int y = startY + 3 + i * 3;
            
            tg.putString(4, y, labels[i] + ": ");
            
            // Показываем введённое значение или подсказку
            if (step < inputStep) {
                tg.setForegroundColor(TextColor.ANSI.GREEN);
                tg.putString(20, y, String.valueOf(inputValues[step]));
                tg.setForegroundColor(TextColor.ANSI.WHITE);
            } else if (step == inputStep) {
                // Текущее поле ввода
                tg.setForegroundColor(TextColor.ANSI.YELLOW);
                tg.putString(20, y, inputBuffer.toString() + "█");
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                // Позиционируем курсор
                screen.setCursorPosition(
                    new com.googlecode.lanterna.TerminalPosition(20 + inputBuffer.length(), y));
            } else {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(20, y, "_");
            }
        }
        
        // Подсказка
        if (inputStep == baseStep + 2) {
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(2, startY + 10, "Нажмите Enter для продолжения...");
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        } else {
            tg.putString(2, startY + 10, "Введите целое число и нажмите Enter");
        }
        
        tg.putString(2, startY + 11, "Esc - отмена");
    }
    
    /**
     * Рисует экран с результатом
     */
    private static void drawResult() {
        int startY = 4;
        
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        String operationName = "";
        char sign = ' ';
        
        switch (menuIndex) {
            case 0: operationName = "СЛОЖЕНИЕ"; sign = '+'; break;
            case 1: operationName = "ВЫЧИТАНИЕ"; sign = '-'; break;
            case 2: operationName = "УМНОЖЕНИЕ"; sign = '×'; break;
            case 3: operationName = "ДЕЛЕНИЕ"; sign = '÷'; break;
            case 4: operationName = "СРАВНЕНИЕ"; sign = '?'; break;
        }
        
        tg.putString(2, startY, "Результат операции: " + operationName);
        tg.putString(2, startY + 1, "──────────────────────────────────────");
        
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        
        if (menuIndex <= 3) {
            // Арифметическая операция
            tg.putString(4, startY + 3, fraction1.toString() + " " + sign + " " + fraction2.toString() + " = " + result.toString());
            tg.putString(4, startY + 5, "Десятичное представление: " + String.format("%.6f", result.toDecimal()));
        } else if (menuIndex == 4) {
            // Сравнение
            int cmp = fraction1.compareTo(fraction2);
            String comparison;
            if (cmp < 0) comparison = " < ";
            else if (cmp > 0) comparison = " > ";
            else comparison = " = ";
            
            tg.putString(4, startY + 3, fraction1.toString() + comparison + fraction2.toString());
            tg.putString(4, startY + 5, "Десятичные: " + 
                String.format("%.6f", fraction1.toDecimal()) + " и " + 
                String.format("%.6f", fraction2.toDecimal()));
        }
        
        tg.putString(2, startY + 8, "Нажмите Enter для возврата в меню");
    }
    
    /**
     * Обработка клавиш в главном меню
     */
    private static void handleMainMenu(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp:
                menuIndex = (menuIndex - 1 + menuItems.length) % menuItems.length;
                break;
            case ArrowDown:
                menuIndex = (menuIndex + 1) % menuItems.length;
                break;
            case Enter:
                if (menuIndex == 5) { // Выход
                    try {
                        screen.stopScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
                // Начинаем ввод дробей
                inputStep = 0;
                inputBuffer = new StringBuilder();
                inputValues = new int[4];
                errorMessage = "";
                mode = Mode.INPUT_FRACTION1;
                break;
            default:
                break;
        }
    }
    
    /**
     * Обработка клавиш при вводе чисел
     */
    private static void handleInput(KeyStroke key) {
        // Esc - отмена
        if (key.getKeyType() == KeyType.Escape) {
            mode = Mode.MAIN_MENU;
            errorMessage = "";
            screen.setCursorPosition(null);
            return;
        }
        
        if (key.getKeyType() == KeyType.Enter) {
            // Ввод очередного числа
            try {
                if (inputBuffer.length() == 0) {
                    errorMessage = "Введите число!";
                    return;
                }
                
                int value = Integer.parseInt(inputBuffer.toString());
                inputValues[inputStep] = value;
                inputStep++;
                inputBuffer = new StringBuilder();
                errorMessage = "";
                
                // Проверяем, все ли числа введены для текущей дроби
                int baseStep = (mode == Mode.INPUT_FRACTION1) ? 0 : 2;
                
                if (inputStep == baseStep + 2) {
                    // Все числа для текущей дроби введены
                    try {
                        if (mode == Mode.INPUT_FRACTION1) {
                            // Создаём первую дробь
                            fraction1 = new Fraction(inputValues[0], inputValues[1]);
                            // Переходим ко второй дроби
                            mode = Mode.INPUT_FRACTION2;
                            inputStep = 2;
                        } else {
                            // Создаём вторую дробь и выполняем операцию
                            fraction2 = new Fraction(inputValues[2], inputValues[3]);
                            performOperation();
                            mode = Mode.SHOW_RESULT;
                            screen.setCursorPosition(null);
                        }
                    } catch (ArithmeticException e) {
                        errorMessage = e.getMessage();
                        inputStep--;
                        inputBuffer = new StringBuilder();
                    }
                }
            } catch (NumberFormatException e) {
                errorMessage = "Введите целое число!";
                inputBuffer = new StringBuilder();
            }
            return;
        }
        
        // Backspace
        if (key.getKeyType() == KeyType.Backspace || key.getKeyType() == KeyType.Delete) {
            if (inputBuffer.length() > 0) {
                inputBuffer.setLength(inputBuffer.length() - 1);
            }
            return;
        }
        
        // Ввод цифр и минуса
        Character c = key.getCharacter();
        if (c != null) {
            if (c == '-' && inputBuffer.length() == 0) {
                inputBuffer.append(c);
            } else if (Character.isDigit(c)) {
                inputBuffer.append(c);
            }
        }
    }
    
    /**
     * Обработка клавиш на экране результата
     */
    private static void handleResult(KeyStroke key) {
        if (key.getKeyType() == KeyType.Enter || key.getKeyType() == KeyType.Escape) {
            mode = Mode.MAIN_MENU;
            screen.setCursorPosition(null);
        }
    }
    
    /**
     * Выполняет выбранную операцию
     */
    private static void performOperation() {
        switch (menuIndex) {
            case 0: result = fraction1.add(fraction2); break;
            case 1: result = fraction1.subtract(fraction2); break;
            case 2: result = fraction1.multiply(fraction2); break;
            case 3: result = fraction1.divide(fraction2); break;
            case 4: result = null; break; // для сравнения не нужен
        }
    }
}