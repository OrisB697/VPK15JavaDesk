package com.javadesk;

import java.util.*;

public class App {
    private static Library library = Library.INSTANCE;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("\nДОМАШНЯЯ БИБЛИОТЕКА");
        System.out.println("=".repeat(40));
        
        boolean running = true;
        
        while (running) {
            showMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    library.displayAllBooks();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    updateStorage();
                    break;
                case 6:
                    showDescription();
                    break;
                case 7:
                    library.saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Ошибка: неверный выбор. Пожалуйста, выберите пункт от 1 до 7.");
            }
        }
        
        scanner.close();
    }
    
    private static void showMenu() {
        System.out.println("\nМЕНЮ:");
        System.out.println("  1. Добавить новую книгу");
        System.out.println("  2. Удалить книгу");
        System.out.println("  3. Просмотреть все книги");
        System.out.println("  4. Найти книгу по названию");
        System.out.println("  5. Изменить место хранения");
        System.out.println("  6. Показать описание книги");
        System.out.println("  7. Выход");
        System.out.print("\nВыберите действие: ");
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void addBook() {
        System.out.println("\nДОБАВЛЕНИЕ НОВОЙ КНИГИ");
        System.out.println("-".repeat(30));
        
        System.out.print("Введите название книги: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Ошибка: название не может быть пустым");
            return;
        }
        
        List<String> authors = inputAuthors();
        if (authors.isEmpty()) {
            System.out.println("Ошибка: должен быть хотя бы один автор");
            return;
        }
        
        Location storage = selectLocation();
        
        System.out.print("Введите описание книги (или оставьте пустым): ");
        String description = scanner.nextLine();
        
        int pageCount = 0;
        System.out.print("Введите количество страниц (или 0): ");
        try {
            pageCount = Integer.parseInt(scanner.nextLine());
            if (pageCount < 0) pageCount = 0;
        } catch (NumberFormatException e) {
            pageCount = 0;
        }
        
        Book book = new Book(name, authors, storage, description, pageCount);
        library.addBook(book);
    }
    
    private static List<String> inputAuthors() {
        List<String> authors = new ArrayList<>();
        System.out.println("Введите авторов (каждого с новой строки, пустая строка - окончание):");
        
        while (true) {
            System.out.print("Автор: ");
            String author = scanner.nextLine().trim();
            
            if (author.isEmpty()) {
                if (authors.isEmpty()) {
                    System.out.println("Должен быть хотя бы один автор!");
                    continue;
                }
                break;
            }
            
            authors.add(author);
        }
        
        return authors;
    }
    
    private static Location selectLocation() {
        System.out.println("\nВыберите место хранения:");
        Location[] locations = Location.values();
        
        for (int i = 0; i < locations.length; i++) {
            System.out.println("  " + (i + 1) + ". " + locations[i].getMessage());
        }
        
        while (true) {
            System.out.print("Ваш выбор (1-" + locations.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= locations.length) {
                    return locations[choice - 1];
                }
                System.out.println("Неверный выбор. Попробуйте снова.");
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }
    }
    
    private static void removeBook() {
        if (library.isEmpty()) {
            System.out.println("\nБиблиотека пуста. Нечего удалять.");
            return;
        }
        
        library.displayAllBooks();
        System.out.print("Введите номер книги для удаления: ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine());
            library.removeBook(index);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректный номер.");
        }
    }
    
    private static void searchBook() {
        System.out.print("\nВведите название книги (или часть названия): ");
        String query = scanner.nextLine();
        library.searchAndDisplay(query);
    }
    
    private static void updateStorage() {
        if (library.isEmpty()) {
            System.out.println("\nБиблиотека пуста. Нечего изменять.");
            return;
        }
        
        library.displayAllBooks();
        System.out.print("Введите номер книги для изменения места хранения: ");
        
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Location newLocation = selectLocation();
            library.updateStorageLocation(index, newLocation);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректный номер.");
        }
    }

    private static void showDescription() {
        if (library.isEmpty()) {
            System.out.println("\n Библиотека пуста, книги не найдены");
            return;
        }
        library.displayAllBooks();
        System.out.println("Выберите книгу, описание которой хотите прочесть: ");

        try {
            int index = Integer.parseInt(scanner.nextLine());
            String desc = library.getBookDescription(index);
            System.out.println(desc);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректный номер.");
        }
    }
}