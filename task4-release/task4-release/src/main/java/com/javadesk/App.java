package com.javadesk;

import com.javadesk.fraction.*;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите первую дробь (числитель и знаменатель через пробел): ");
        int num1 = scanner.nextInt();
        int den1 = scanner.nextInt();

        System.out.print("Введите вторую дробь: ");
        int num2 = scanner.nextInt();
        int den2 = scanner.nextInt();

        Fraction f1 = new Fraction(num1, den1);
        Fraction f2 = new Fraction(num2, den2);

        System.out.print("Выберите операцию (+, -, *, /): ");
        char op = scanner.next().charAt(0);

        Fraction result;
        switch (op) {
            case '+': result = f1.add(f2); break;
            case '-': result = f1.subtract(f2); break;
            case '*': result = f1.multiply(f2); break;
            case '/': result = f1.divide(f2); break;
            default:
                System.out.println("Неизвестная операция");
                scanner.close();
                return;
        }

        System.out.println(f1 + " " + op + " " + f2 + " = " + result);
        System.out.println("Десятичное представление: " + result.toDecimal());

        // Сравнение
        int cmp = f1.compareTo(f2);
        if (cmp < 0) System.out.println(f1 + " < " + f2);
        else if (cmp > 0) System.out.println(f1 + " > " + f2);
        else System.out.println(f1 + " = " + f2);

        scanner.close();
    }
}