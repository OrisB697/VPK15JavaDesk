/*package com.javadesk;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 *//*
public class AppTest 
{
    /**
     * Rigorous Test :-)
     *//*
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}*/
package com.javadesk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
import com.javadesk.fraction.*;

class AppTest {

    // ==================== КОНСТРУКТОР ====================
    
    @Nested
    @DisplayName("Конструктор")
    class ConstructorTests {
        
        @Test
        @DisplayName("Создание положительной дроби")
        void positiveFraction() {
            Fraction f = new Fraction(3, 4);
            assertEquals(3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Создание отрицательной дроби (числитель)")
        void negativeNumerator() {
            Fraction f = new Fraction(-3, 4);
            assertEquals(-3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Создание отрицательной дроби (знаменатель)")
        void negativeDenominator() {
            Fraction f = new Fraction(3, -4);
            assertEquals(-3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Оба отрицательные")
        void bothNegative() {
            Fraction f = new Fraction(-3, -4);
            assertEquals(3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Сокращение дроби")
        void fractionReduction() {
            Fraction f = new Fraction(6, 8);
            assertEquals(3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Отрицательная дробь с сокращением")
        void negativeFractionReduction() {
            Fraction f = new Fraction(-6, 8);
            assertEquals(-3, f.getNumerator());
            assertEquals(4, f.getDenominator());
        }
        
        @Test
        @DisplayName("Целое число (знаменатель = 1)")
        void wholeNumber() {
            Fraction f = new Fraction(5, 1);
            assertEquals(5, f.getNumerator());
            assertEquals(1, f.getDenominator());
        }
        
        @Test
        @DisplayName("Ноль в числителе")
        void zeroNumerator() {
            Fraction f = new Fraction(0, 5);
            assertEquals(0, f.getNumerator());
            assertEquals(1, f.getDenominator());
        }
        
        @Test
        @DisplayName("Знаменатель равен 0 (ошибка)")
        void denominatorZero() {
            ArithmeticException ex = assertThrows(ArithmeticException.class, 
                () -> new Fraction(1, 0));
            assertEquals("Знаменатель не может быть равен нулю", ex.getMessage());
        }
    }

    // ==================== АРИФМЕТИЧЕСКИЕ ОПЕРАЦИИ ====================
    
    @Nested
    @DisplayName("Сложение")
    class AddTests {
        
        @Test
        @DisplayName("Положительные дроби")
        void addPositive() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(1, 2);
            Fraction result = a.add(b);
            assertEquals(3, result.getNumerator());
            assertEquals(4, result.getDenominator());
        }
        
        @Test
        @DisplayName("С отрицательными числами")
        void addWithNegative() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(-1, 2);
            Fraction result = a.add(b);
            assertEquals(-1, result.getNumerator());
            assertEquals(4, result.getDenominator());
        }
        
        @Test
        @DisplayName("Сложение с нулём")
        void addWithZero() {
            Fraction a = new Fraction(0, 1);
            Fraction b = new Fraction(1, 2);
            Fraction result = a.add(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
        
        @Test
        @DisplayName("Результат с сокращением")
        void addWithReduction() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(1, 4);
            Fraction result = a.add(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
    }

    @Nested
    @DisplayName("Вычитание")
    class SubtractTests {
        
        @Test
        @DisplayName("Положительные дроби")
        void subtractPositive() {
            Fraction a = new Fraction(3, 4);
            Fraction b = new Fraction(1, 4);
            Fraction result = a.subtract(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
        
        @Test
        @DisplayName("Результат отрицательный")
        void subtractToNegative() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(3, 4);
            Fraction result = a.subtract(b);
            assertEquals(-1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
        
        @Test
        @DisplayName("Вычитание отрицательного")
        void subtractNegative() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(-1, 4);
            Fraction result = a.subtract(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
    }

    @Nested
    @DisplayName("Умножение")
    class MultiplyTests {
        
        @Test
        @DisplayName("Положительные дроби")
        void multiplyPositive() {
            Fraction a = new Fraction(2, 3);
            Fraction b = new Fraction(3, 4);
            Fraction result = a.multiply(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
        
        @Test
        @DisplayName("Умножение на ноль")
        void multiplyByZero() {
            Fraction a = new Fraction(0, 1);
            Fraction b = new Fraction(3, 4);
            Fraction result = a.multiply(b);
            assertEquals(0, result.getNumerator());
            assertEquals(1, result.getDenominator());
        }
        
        @Test
        @DisplayName("Отрицательное умножение")
        void multiplyNegative() {
            Fraction a = new Fraction(-2, 3);
            Fraction b = new Fraction(3, 4);
            Fraction result = a.multiply(b);
            assertEquals(-1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
        
        @Test
        @DisplayName("Умножение двух отрицательных")
        void multiplyBothNegative() {
            Fraction a = new Fraction(-2, 3);
            Fraction b = new Fraction(-3, 4);
            Fraction result = a.multiply(b);
            assertEquals(1, result.getNumerator());
            assertEquals(2, result.getDenominator());
        }
    }

    @Nested
    @DisplayName("Деление")
    class DivideTests {
        
        @Test
        @DisplayName("Деление положительных дробей")
        void dividePositive() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(1, 4);
            Fraction result = a.divide(b);
            assertEquals(2, result.getNumerator());
            assertEquals(1, result.getDenominator());
        }
        
        @Test
        @DisplayName("Деление на отрицательную дробь")
        void divideByNegative() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(-1, 4);
            Fraction result = a.divide(b);
            assertEquals(-2, result.getNumerator());
            assertEquals(1, result.getDenominator());
        }
        
        @Test
        @DisplayName("Деление на ноль (числитель другой дроби = 0)")
        void divideByZeroFraction() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(0, 1);
            ArithmeticException ex = assertThrows(ArithmeticException.class, 
                () -> a.divide(b));
            assertEquals("Деление на ноль", ex.getMessage());
        }
    }

    // ==================== TODECIMAL ====================
    
    @Test
    @DisplayName("Преобразование в десятичное число")
    void toDecimal() {
        Fraction f = new Fraction(1, 4);
        assertEquals(0.25, f.toDecimal(), 0.0001);
    }
    
    @Test
    @DisplayName("Преобразование отрицательной дроби")
    void toDecimalNegative() {
        Fraction f = new Fraction(-1, 2);
        assertEquals(-0.5, f.toDecimal(), 0.0001);
    }

    // ==================== СРАВНЕНИЕ ====================
    
    @Nested
    @DisplayName("CompareTo")
    class CompareToTests {
        
        @Test
        @DisplayName("Меньше")
        void lessThan() {
            Fraction a = new Fraction(1, 4);
            Fraction b = new Fraction(1, 2);
            assertTrue(a.compareTo(b) < 0);
        }
        
        @Test
        @DisplayName("Больше")
        void greaterThan() {
            Fraction a = new Fraction(3, 4);
            Fraction b = new Fraction(1, 2);
            assertTrue(a.compareTo(b) > 0);
        }
        
        @Test
        @DisplayName("Равны")
        void equal() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(2, 4);
            assertEquals(0, a.compareTo(b));
        }
        
        @Test
        @DisplayName("Отрицательные дроби")
        void negativeFractions() {
            Fraction a = new Fraction(-1, 2);
            Fraction b = new Fraction(1, 4);
            assertTrue(a.compareTo(b) < 0);
        }
    }

    // ==================== EQUALS/HASHCODE ====================
    
    @Nested
    @DisplayName("Equals/HashCode")
    class EqualsHashCodeTests {
        
        @Test
        @DisplayName("Равные дроби")
        void equalFractions() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(2, 4);
            assertEquals(a, b);
            assertEquals(a.hashCode(), b.hashCode());
        }
        
        @Test
        @DisplayName("Неравные дроби")
        void notEqualFractions() {
            Fraction a = new Fraction(1, 2);
            Fraction b = new Fraction(1, 3);
            assertNotEquals(a, b);
        }
        
        @Test
        @DisplayName("Сравнение с null")
        void compareWithNull() {
            Fraction a = new Fraction(1, 2);
            assertNotEquals(null, a);
        }
        
        @Test
        @DisplayName("Сравнение с объектом другого типа")
        void compareWithDifferentType() {
            Fraction a = new Fraction(1, 2);
            assertNotEquals("1/2", a);
        }
        
        @Test
        @DisplayName("Сравнение с собой")
        void compareWithSelf() {
            Fraction a = new Fraction(1, 2);
            assertEquals(a, a);
        }
    }

    // ==================== TOSTRING ====================
    
    @Nested
    @DisplayName("ToString")
    class ToStringTests {
        
        @Test
        @DisplayName("Обычная дробь")
        void regularFraction() {
            Fraction f = new Fraction(3, 4);
            assertEquals("3/4", f.toString());
        }
        
        @Test
        @DisplayName("Целое число")
        void wholeNumber() {
            Fraction f = new Fraction(5, 1);
            assertEquals("5", f.toString());
        }
        
        @Test
        @DisplayName("Отрицательная дробь")
        void negativeFraction() {
            Fraction f = new Fraction(-3, 4);
            assertEquals("-3/4", f.toString());
        }
        
        @Test
        @DisplayName("Ноль")
        void zero() {
            Fraction f = new Fraction(0, 1);
            assertEquals("0", f.toString());
        }
    }

    // ==================== ГЕТТЕРЫ ====================
    
    @Test
    @DisplayName("Геттеры")
    void getters() {
        Fraction f = new Fraction(3, 4);
        assertEquals(3, f.getNumerator());
        assertEquals(4, f.getDenominator());
    }

    // ==================== ДОПОЛНИТЕЛЬНЫЕ ПРОВЕРКИ ====================
    
    @Test
    @DisplayName("Цепочка операций")
    void chainOperations() {
        Fraction a = new Fraction(1, 2);
        Fraction b = new Fraction(1, 4);
        Fraction c = new Fraction(1, 8);
        
        Fraction result = a.add(b).divide(c);
        assertEquals(6, result.getNumerator());
        assertEquals(1, result.getDenominator());
    }
}