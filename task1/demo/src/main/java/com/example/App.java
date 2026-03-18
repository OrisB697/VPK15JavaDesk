package com.example;

import java.util.Scanner;

public class App {
    
    /**
     * Находит все слова в предложении, содержащие указанную подстроку
     * @param sentence предложение для поиска
     * @param substring подстрока для поиска
     * @return строку с найденными словами, разделенными переносом строки
     */
    public static String findWords(String sentence, String substring) {
        // Разбиваем предложение на отдельные слова
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();
        
        // Проходим по каждому слову и проверяем наличие подстроки
        for (String word : words) {
            // Сравниваем без учета регистра
            if (word.toLowerCase().contains(substring.toLowerCase())) {
                result.append(word).append("\n");
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Получаем входные данные от пользователя
        System.out.println("Enter your sentence: ");
        String sentence = scanner.nextLine();
        
        System.out.println("Enter substring: ");
        String substring = scanner.nextLine();
        
        scanner.close();
        
        // Выводим результат поиска
        String result = findWords(sentence, substring);
        System.out.print(result);
    }
}