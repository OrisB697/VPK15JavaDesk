package com.javadesk.fraction;

public class Fraction implements Comparable<Fraction> {
    private final int numerator;   // числитель (хранит знак в том числе)
    private final int denominator; // знаменатель

    /**
     * Создаёт дробь numerator/denominator.
     * @throws ArithmeticException если denominator == 0
     */
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Знаменатель не может быть равен нулю");
        }
        // Приводим знак: знаменатель делаем положительным, знак переносим в числитель
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        // Сокращаем дробь
        int gcd = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    // ---------- Арифметические операции (возвращают новую дробь) ----------

    public Fraction add(Fraction other) {
        int num = this.numerator * other.denominator + other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    public Fraction subtract(Fraction other) {
        int num = this.numerator * other.denominator - other.numerator * this.denominator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    public Fraction multiply(Fraction other) {
        int num = this.numerator * other.numerator;
        int den = this.denominator * other.denominator;
        return new Fraction(num, den);
    }

    public Fraction divide(Fraction other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        int num = this.numerator * other.denominator;
        int den = this.denominator * other.numerator;
        return new Fraction(num, den);
    }

    // ---------- Преобразование в десятичное число ----------

    /**
     * Возвращает приближённое десятичное представление дроби.
     */
    public double toDecimal() {
        return (double) numerator / denominator;
    }

    // ---------- Сравнение ----------

    @Override
    public int compareTo(Fraction other) {
        // Сравниваем через приведение к общему знаменателю, чтобы избежать потери точности
        long left = (long) this.numerator * other.denominator;
        long right = (long) other.numerator * this.denominator;
        return Long.compare(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Fraction)) return false;
        Fraction other = (Fraction) obj;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return 31 * numerator + denominator;
    }

    // ---------- Строковое представление ----------

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }

    // ---------- Вспомогательные методы ----------

    /**
     * Алгоритм Евклида для нахождения НОД двух положительных чисел.
     */
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Геттеры (при необходимости)
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}