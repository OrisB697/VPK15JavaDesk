package com.javadesk;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public enum Library {
    INSTANCE;  
    
    private List<Book> books;
    private static final String FILE_NAME = "library.dat";
    
    Library() {
        books = new ArrayList<>();
        loadFromFile();
    }
    
    public void addBook(Book book) {
        if (book == null) {
            System.out.println("Ошибка: книга не может быть null");
            return;
        }
        
        if (!book.isValid()) {
            System.out.println("Ошибка: книга содержит некорректные данные");
            System.out.println("  - Название не должно быть пустым");
            System.out.println("  - Должен быть хотя бы один автор");
            System.out.println("  - Место хранения должно быть указано");
            return;
        }
        
        books.add(book);
        saveToFile();
        System.out.println("Книга успешно добавлена: \"" + book.getName() + "\"");
    }
    
    public boolean removeBook(int index) {
        if (index < 1 || index > books.size()) {
            System.out.println("Ошибка: неверный номер книги. Доступны номера от 1 до " + books.size());
            return false;
        }
        
        Book removed = books.remove(index - 1);
        saveToFile();
        System.out.println("Книга удалена: \"" + removed.getName() + "\"");
        return true;
    }
    
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("\n📚 Библиотека пуста. Добавьте книги с помощью пункта 1.\n");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ВСЕ КНИГИ В БИБЛИОТЕКЕ (" + books.size() + " шт.)");
        System.out.println("=".repeat(50));
        
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).toString(i + 1));
        }
        System.out.println("=".repeat(50) + "\n");
    }

    public List<Book> searchByTitle(String query) {
        if (query == null || query.trim().isEmpty()) {
            System.out.println("Ошибка: поисковый запрос не может быть пустым");
            return new ArrayList<>();
        }
        
        String lowerQuery = query.toLowerCase().trim();
        List<Book> results = books.stream()
            .filter(book -> book.getName().toLowerCase().contains(lowerQuery))
            .collect(Collectors.toList());
        
        return results;
    }

    public void searchAndDisplay(String query) {
        List<Book> results = searchByTitle(query);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("РЕЗУЛЬТАТЫ ПОИСКА: \"" + query + "\"");
        System.out.println("=".repeat(50));
        
        if (results.isEmpty()) {
            System.out.println("Книги с таким названием не найдены.");
        } else {
            System.out.println("Найдено книг: " + results.size());
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i).toString(i + 1));
            }
        }
        System.out.println("=".repeat(50) + "\n");
    }

    public boolean updateStorageLocation(int index, Location newLocation) {
        if (index < 1 || index > books.size()) {
            System.out.println("Ошибка: неверный номер книги. Доступны номера от 1 до " + books.size());
            return false;
        }
        
        if (newLocation == null) {
            System.out.println("Ошибка: место хранения не может быть null");
            return false;
        }
        
        Book book = books.get(index - 1);
        String oldLocation = book.getStorage().getMessage();
        book.setStorage(newLocation);
        saveToFile();
        
        System.out.println(" Место хранения изменено:");
        System.out.println("  Книга: \"" + book.getName() + "\"");
        System.out.println("  Было: " + oldLocation);
        System.out.println("  Стало: " + newLocation.getMessage());
        return true;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book getBook(int index) {
        if (index < 1 || index > books.size()) {
            return null;
        }
        return books.get(index - 1);
    }

    public int getSize() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }

    public void clearLibrary() {
        books.clear();
        saveToFile();
        System.out.println("Библиотека очищена");
    }

    public String getBookDescription(int i) {
        Book book = getBook(i);
        return book.getDescription();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
            System.out.println("Данные сохранены. Всего книг: " + books.size());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }
    
    //@SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
                System.out.println("Загружено книг: " + books.size());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Ошибка загрузки данных: " + e.getMessage());
                books = new ArrayList<>();
            }
        } else {
            System.out.println("Файл с данными не найден. Создана новая библиотека.");
        }
    }
    
    public void saveAndExit() {
        saveToFile();
        System.out.println("Работа завершена.");
    }
}