package com.javadesk;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConversionResultPrinterTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;
    private ConversionResultPrinter printer;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        printer = new ConversionResultPrinter();
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }


    @Test
    @DisplayName("Вывод сообщения об ошибке")
    void testPrintError() {
        printer.printError("Неподдерживаемая единица измерения");
        String output = outputStream.toString().trim();
        assertEquals("Ошибка: Неподдерживаемая единица измерения", output);
    }

}