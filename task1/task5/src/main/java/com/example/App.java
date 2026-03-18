package com.example;

import java.util.Scanner;

public class App {
    
    /**
     * Обрабатывает строку, заменяя символы на скобки в зависимости от частоты их появления
     * @param input входная строка для обработки
     * @return строка с замененными символами на '(' или ')'
     */
    public static String processBrackets(String input) {
        // Проверка на null
        if (input == null) {
            return "";
        }
        
        // Подсчет открывающих и закрывающих скобок во входной строке
        int bracket = 0;
        int reversBracket = 0;
        
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ')') {
                reversBracket += 1;
            } else if (input.charAt(i) == '(') {
                bracket++;
            }
        }
        
        String result = input;
        
        // Замена всех '(' на ')' если их больше одного
        if (bracket > 1) {
            result = result.replace('(', ')');
        }
        
        // Замена всех ')' на '(' если закрывающая скобка одна
        if (reversBracket == 1) {
            result = result.replace(')', '(');
        }
        
        // Замена символов на скобки в зависимости от частоты их появления
        for (int i = 0; i < result.length(); i++) {
            if (countChar(result, result.charAt(i)) > 1) {
                result = result.replace(result.charAt(i), ')');
            } else {
                result = result.replace(result.charAt(i), '(');
            }
        }
        
        return result;
    }
    
    /**
     * Подсчитывает количество вхождений символа в строке
     * @param text строка для поиска
     * @param target искомый символ
     * @return количество вхождений символа
     */
    public static int countChar(String text, char target) {
        // Проверка на null
        if (text == null) {
            return 0;
        }
        
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Ввод строки от пользователя
        System.out.println("Enter your sentence: ");
        String sentence = scanner.nextLine();
        
        scanner.close();
        
        // Обработка и вывод результата
        String result = processBrackets(sentence);
        System.out.println(result);
    }
}