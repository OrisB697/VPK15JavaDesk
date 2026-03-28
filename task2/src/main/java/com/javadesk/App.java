package com.javadesk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Абстрактный базовый класс для геометрических фигур.
 * Определяет контракт для всех фигур через абстрактные методы.
 * Использует шаблонный метод displayInfo() для единообразного вывода информации.
 */
abstract class Figure {
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract String getName();
    
    /**
     * Шаблонный метод для отображения информации о фигуре.
     * Использует абстрактные методы, реализуемые в подклассах.
     */
    public void displayInfo() {
        System.out.printf("%s: Площадь = %.2f, Периметр = %.2f%n", 
                          getName(), getArea(), getPerimeter());
    }
}

/**
 * Реализация прямоугольника.
 * Immutable объект - после создания размеры изменить нельзя.
 */
class Rectangle extends Figure {
    private final double width;  // final для иммутабельности
    private final double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double getArea() {
        return width * height;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public String getName() {
        return "Прямоугольник";
    }
}

/**
 * Реализация круга.
 * Immutable объект.
 */
class Circle extends Figure {
    private final double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public String getName() {
        return "Круг";
    }
}

/**
 * Реализация треугольника по трем сторонам.
 * Содержит валидацию существования треугольника при создании.
 */
class Triangle extends Figure {
    private final double sideA;
    private final double sideB;
    private final double sideC;
    

    public Triangle(double sideA, double sideB, double sideC) {
        if (!isValidTriangle(sideA, sideB, sideC)) {
            throw new IllegalArgumentException("Треугольник с такими сторонами не существует");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    
    /**
     * Проверка неравенства треугольника.
     * Каждая сторона должна быть меньше суммы двух других.
     */
    private boolean isValidTriangle(double a, double b, double c) {
        return a + b > c && a + c > b && b + c > a;
    }
    
    @Override
    public double getArea() {
        // Формула Герона
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }
    
    @Override
    public String getName() {
        return "Треугольник";
    }
}

//==========ЗАДАНИЕ 5===========//

/**
 * Класс для работы с трехмерными векторами.
 * Предоставляет полный набор векторных операций.
 * Immutable - все операции возвращают новые объекты.
 */
class Vector3D {
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Конструктор от компонент вектора.
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Конструктор от двух точек (вектор из start в end).
     * Позволяет создавать векторы, более интуитивно понятные в геометрическом контексте.
     */
    public Vector3D(Point3D start, Point3D end) {
        this.x = end.getX() - start.getX();
        this.y = end.getY() - start.getY();
        this.z = end.getZ() - start.getZ();
    }
    
    // Геттеры для доступа к компонентам
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    
    /**
     * Сложение векторов.
     * @return новый вектор, представляющий сумму
     */
    public Vector3D add(Vector3D other) {
        return new Vector3D(x + other.x, y + other.y, z + other.z);
    }
    
    /**
     * Вычитание векторов.
     * @return новый вектор, представляющий разность
     */
    public Vector3D subtract(Vector3D other) {
        return new Vector3D(x - other.x, y - other.y, z - other.z);
    }
    
    /**
     * Скалярное произведение.
     * @return скаляр (double)
     */
    public double dotProduct(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }
    
    /**
     * Длина (модуль) вектора.
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * Косинус угла между векторами.
     * Защита от деления на ноль и численной нестабильности.
     */
    public double cosineBetween(Vector3D other) {
        double dot = this.dotProduct(other);
        double lengths = this.length() * other.length();
        
        // Защита от деления на ноль
        if (lengths == 0) {
            return 0;
        }
        
        // Ограничиваем значение в пределах [-1, 1] из-за погрешностей вычислений с плавающей точкой
        double cosine = dot / lengths;
        return Math.max(-1.0, Math.min(1.0, cosine));
    }
    
    /**
     * Угол между векторами в радианах.
     */
    public double angleBetween(Vector3D other) {
        return Math.acos(this.cosineBetween(other));
    }
    
    /**
     * Проверка ортогональности векторов.
     * Использует epsilon для сравнения с нулем из-за погрешностей вычислений.
     */
    public boolean isOrthogonal(Vector3D other) {
        return Math.abs(this.dotProduct(other)) < 1e-10;
    }
    
    /**
     * Проверка коллинеарности векторов.
     * Векторы коллинеарны, если их компоненты пропорциональны.
     */
    public boolean isCollinear(Vector3D other) {
        if (this.length() == 0 || other.length() == 0) return true;
        
        // Проверяем пропорциональность компонент с учетом погрешности
        double ratioX = this.x / other.x;
        double ratioY = this.y / other.y;
        double ratioZ = this.z / other.z;
        
        // Все компоненты должны быть пропорциональны с одинаковым коэффициентом
        return Math.abs(ratioX - ratioY) < 1e-10 && 
               Math.abs(ratioX - ratioZ) < 1e-10;
    }
    
    /**
     * Нормализация вектора (получение единичного вектора направления).
     * @return вектор с длиной 1, сохраняющий направление
     */
    public Vector3D normalize() {
        double len = length();
        if (len == 0) {
            // Возвращаем нулевой вектор для нулевого входа
            return new Vector3D(0, 0, 0);
        }
        return new Vector3D(x / len, y / len, z / len);
    }
    
    /**
     * Векторное произведение.
     * @return новый вектор, перпендикулярный обоим исходным
     */
    public Vector3D crossProduct(Vector3D other) {
        return new Vector3D(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }
    
    @Override
    public String toString() {
        // Используем Locale.US для единообразного форматирования независимо от локали
        return String.format(Locale.US, "Vector(%.2f, %.2f, %.2f)", x, y, z);
    }
    
    /**
     * Сравнение векторов с учетом погрешности вычислений.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Vector3D other = (Vector3D) obj;
        // Сравнение с epsilon из-за погрешностей double
        return Math.abs(x - other.x) < 1e-10 &&
               Math.abs(y - other.y) < 1e-10 &&
               Math.abs(z - other.z) < 1e-10;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }
}

/**
 * Простой immutable класс для представления точки в 3D пространстве.
 */
class Point3D {
    private final double x;
    private final double y;
    private final double z;
    
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    
    @Override
    public String toString() {
        return String.format(Locale.US, "Point(%.2f, %.2f, %.2f)", x, y, z);
    }
}

/**
 * Главный класс приложения с демонстрацией работы.
 */
public class App {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            printMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    demonstrateFigures();
                    break;
                case 2:
                    demonstrateVectors();
                    break;
                case 0:
                    running = false;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите 1, 2 или 0.");
            }
            
            if (running && choice != 0) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Вывод меню выбора режима работы.
     */
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ВЫБЕРИТЕ РЕЖИМ РАБОТЫ:");
        System.out.println("=".repeat(50));
        System.out.println("1 - Демонстрация работы с геометрическими фигурами");
        System.out.println("2 - Демонстрация работы с 3D векторами");
        System.out.println("0 - Выход из программы");
        System.out.println("-".repeat(50));
    }
    
    /**
     * Получение выбора пользователя с обработкой ошибок ввода.
     */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Демонстрация работы с геометрическими фигурами.
     */
    private static void demonstrateFigures() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ДЕМОНСТРАЦИЯ РАБОТЫ С ГЕОМЕТРИЧЕСКИМИ ФИГУРАМИ");
        System.out.println("=".repeat(50));
        
        // Демонстрация полиморфизма - работа с коллекцией фигур через абстрактный тип
        List<Figure> figures = new ArrayList<>();
        
        // Создание различных фигур
        figures.add(new Rectangle(5, 3));
        figures.add(new Circle(4));
        figures.add(new Triangle(3, 4, 5));
        figures.add(new Rectangle(7, 2));
        figures.add(new Circle(2.5));
        
        System.out.println("\nИнформация о фигурах:");
        System.out.println("---------------------");
        
        for (Figure figure : figures) {
            figure.displayInfo();
        }
        
        System.out.println("\nОбщая статистика:");
        System.out.println("-----------------");
        System.out.printf("Всего фигур: %d%n", figures.size());
        
        double totalArea = 0;
        double totalPerimeter = 0;
        
        for (Figure figure : figures) {
            totalArea += figure.getArea();
            totalPerimeter += figure.getPerimeter();
        }
        
        System.out.printf("Общая площадь всех фигур: %.2f%n", totalArea);
        System.out.printf("Общий периметр всех фигур: %.2f%n", totalPerimeter);
    }
    
    /**
     * Демонстрация работы с 3D векторами.
     */
    private static void demonstrateVectors() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ДЕМОНСТРАЦИЯ РАБОТЫ С 3D ВЕКТОРАМИ");
        System.out.println("=".repeat(50));
        
        System.out.println("\n1. Создание векторов через точки:");
        Point3D a = new Point3D(1, 2, 3);
        Point3D b = new Point3D(4, 5, 6);
        Point3D c = new Point3D(2, 4, 1);
        Point3D d = new Point3D(5, 3, 7);
        
        Vector3D v1 = new Vector3D(a, b);
        Vector3D v2 = new Vector3D(c, d);
        
        System.out.println("Точка A: " + a);
        System.out.println("Точка B: " + b);
        System.out.println("Вектор AB: " + v1);
        System.out.println("Точка C: " + c);
        System.out.println("Точка D: " + d);
        System.out.println("Вектор CD: " + v2);
        
        // Демонстрация всех операций
        System.out.println("\n2. Длина векторов:");
        System.out.println("|AB| = " + String.format("%.2f", v1.length()));
        System.out.println("|CD| = " + String.format("%.2f", v2.length()));
        
        System.out.println("\n3. Сложение векторов:");
        Vector3D sum = v1.add(v2);
        System.out.println(v1 + " + " + v2 + " = " + sum);
        
        System.out.println("\n4. Вычитание векторов:");
        Vector3D diff = v1.subtract(v2);
        System.out.println(v1 + " - " + v2 + " = " + diff);
        
        System.out.println("\n5. Скалярное произведение:");
        double dot = v1.dotProduct(v2);
        System.out.println(v1 + " · " + v2 + " = " + String.format("%.2f", dot));
        
        System.out.println("\n6. Косинус угла между векторами:");
        double cos = v1.cosineBetween(v2);
        double angle = v1.angleBetween(v2);
        System.out.println("cos(угла) = " + String.format("%.4f", cos));
        System.out.println("Угол = " + String.format("%.2f", Math.toDegrees(angle)) + "°");
        
        System.out.println("\n7. Векторное произведение:");
        Vector3D cross = v1.crossProduct(v2);
        System.out.println(v1 + " × " + v2 + " = " + cross);
        
        System.out.println("\n8. Нормализация векторов:");
        Vector3D v1Norm = v1.normalize();
        System.out.println("Нормализованный " + v1 + " = " + v1Norm);
        System.out.println("Длина нормализованного вектора = " + 
                          String.format("%.2f", v1Norm.length()));
        
        System.out.println("\n9. Проверка на ортогональность:");
        System.out.println("Векторы " + v1 + " и " + v2 + " ортогональны? " + 
                          (v1.isOrthogonal(v2) ? "Да" : "Нет"));
        
        // Демонстрация частного случая - ортогональные векторы
        System.out.println("\n10. Пример с ортогональными векторами:");
        Vector3D v3 = new Vector3D(new Point3D(0, 0, 0), new Point3D(2, 0, 0));
        Vector3D v4 = new Vector3D(new Point3D(0, 0, 0), new Point3D(0, 3, 0));
        System.out.println("v3 = " + v3);
        System.out.println("v4 = " + v4);
        System.out.println("Скалярное произведение v3·v4 = " + 
                          String.format("%.2f", v3.dotProduct(v4)));
        System.out.println("Векторы ортогональны? " + 
                          (v3.isOrthogonal(v4) ? "Да" : "Нет"));
        System.out.println("Косинус угла = " + 
                          String.format("%.4f", v3.cosineBetween(v4)));
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Все операции с векторами выполнены успешно!");
    }
}