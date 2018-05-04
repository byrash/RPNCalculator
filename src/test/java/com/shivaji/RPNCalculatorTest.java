package com.shivaji;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shivaji.datastructure.RPNStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Shivaji
 */
class RPNCalculatorTest {

    private RPNCalculator objUnderTest;

    @BeforeEach
    void setUp() {
        // New object for every operation
        objUnderTest = new RPNCalculator(new RPNStack<>());
    }

    @AfterEach
    void tearDown() {
        // Cleanup after every test
        objUnderTest = null;
    }

    @Test
    void calculate_Example1() {
        assertEquals("5 2", objUnderTest.calculate("5 2"), "Expected stack to have 5 & 2 only");
    }

    @Test
    void calculate_Example2() {
        assertEquals(
            "1.4142135623",
            objUnderTest.calculate("2 sqrt").toString(),
            "Expected stack to have 1.4142135623 only");
        assertEquals("3", objUnderTest.calculate("clear 9 sqrt"), "Expected stack to have 3 only");
    }

    @Test
    void calculate_Example3() {
        assertEquals("3", objUnderTest.calculate("5 2 -"), "Expected stack to have 3 only");
        assertEquals("0", objUnderTest.calculate("3 -"), "Expected stack to have 0 only");
        assertEquals("", objUnderTest.calculate("clear"), "Expected stack to be empty");
    }

    @Test
    void calculate_Example4() {
        assertEquals(
            "5 4 3 2",
            objUnderTest.calculate("5 4 3 2").toString(),
            "Expected stack to have 5 4 3 2 only");
        assertEquals("20", objUnderTest.calculate("undo undo *"), "Expected stack to have 20 only");
        assertEquals("100", objUnderTest.calculate("5 *"), "Expected stack to to have 100 only");
        assertEquals("20 5", objUnderTest.calculate("undo"), "Expected stack to to have 20,5 only");
    }

    @Test
    void calculate_Example5() {
        assertEquals("7 6", objUnderTest.calculate("7 12 2 /"), "Expected stack to have 7 & 6 only");
        assertEquals("42", objUnderTest.calculate("*"), "Expected stack to have 42 only");
        assertEquals("10.5", objUnderTest.calculate("4 /"), "Expected stack to to have 10.5 only");
    }

    @Test
    void calculate_Example6() {
        assertEquals(
            "1 2 3 4 5", objUnderTest.calculate("1 2 3 4 5"), "Expected stack to have 1,2,3,4,5 only");
        assertEquals("1 2 3 20", objUnderTest.calculate("*"), "Expected stack to have 1 2 3 20 only");
        assertEquals("-1", objUnderTest.calculate("clear 3 4 -"), "Expected stack to to have - 1 only");
    }

    @Test
    void calculate_Example7() {
        assertEquals(
            "1 2 3 4 5", objUnderTest.calculate("1 2 3 4 5"), "Expected stack to have 1,2,3,4,5 only");
        assertEquals("120", objUnderTest.calculate("* * * *"), "Expected stack to have 120 only");
    }

    @Test
    void calculate_Example8() {
        assertEquals(
            "11",
            objUnderTest.calculate("1 2 3 * 5 + * * 6 5"),
            "Expected stack to have 11 only");
        // operator * (position: 15): insucient parameters
        // stack: 11
        // (the 6 and 5 were not pushed on to the stack due to the previous error)
    }

    @Test
    void calculate_WithJunkInput() {
        assertEquals(
            "6", objUnderTest.calculate("1 2 3 12.3.4.5.5 junk + +"), "Expected stack to have 6 only");
    }

    @Test
    void calculate_WithDecimals() {
        assertEquals(
            "-2.026348965",
            objUnderTest.calculate(
                "1.9876543 2.12456789012345678912345678 3.987654321965432198765432198765432 10 + + sqrt -"),
            "Expected stack to have -2.026348965 only");
    }

    @Test
    void calculate_WithAllJunk() {
        assertEquals(
            EMPTY,
            objUnderTest.calculate(
                "qwqwqw qwqwqw qwqwqwqw qwqwqw"),
            "Expected stack to be empty");
    }
}
