package com.shivaji.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TDD for RPNStack
 *
 * @author Shivaji
 */
class RPNStackTest {

  private RPNStack objUnderTest;

  @BeforeEach
  void setUp() {
    // New object for every operation
    objUnderTest = new RPNStack();
    objUnderTest.push(Optional.of(BigDecimal.ONE));
    objUnderTest.push(Optional.of(BigDecimal.ZERO));
    objUnderTest.push(Optional.of(BigDecimal.TEN));
  }

  @AfterEach
  void tearDown() {
    // Cleanup after every test
    objUnderTest = null;
  }

  @Test
  void push() {
    assertEquals(3, objUnderTest.size(), "Should have 3 items");
  }


  @Test
  void isEmpty() {
    assertFalse(objUnderTest.isEmpty(), "Cant be empty");
    objUnderTest.clear();
    assertTrue(objUnderTest.isEmpty(), "should be empty");
  }

  @Test
  void list() {
    assertEquals(
        Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN),
        objUnderTest.list(),
        "Objects are not listed right");
  }

  @Test
  void peek() {
    assertEquals(Optional.of(BigDecimal.TEN), objUnderTest.peak(), "Last item should 10");
    assertEquals(3, objUnderTest.size(), "Peek should not have removed item");
  }

  @Test
  void size() {
    objUnderTest.push(Optional.of(BigDecimal.ONE));
    assertEquals(4, objUnderTest.size(), "Should have 4 items");
  }

  @Test
  void clear() {
    assertEquals(3, objUnderTest.size(), "Should have 3 items");
    objUnderTest.clear();
    assertEquals(0, objUnderTest.size(), "Should have 0 items");
  }

  @Test
  void undo() {
    assertEquals(3, objUnderTest.size(), "Should have 3 elements");
    assertEquals(
        Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN),
        objUnderTest.list(),
        "Objects are not listed right");
    assertTrue(objUnderTest.undo());
    assertEquals(2, objUnderTest.size(), "Should have 2 elements");
    assertEquals(
        Arrays.asList(BigDecimal.ONE, BigDecimal.ZERO),
        objUnderTest.list(),
        "Objects are not listed right");
    assertTrue(objUnderTest.undo());
    assertEquals(1, objUnderTest.size(), "Should have 1 elements");
    assertEquals(
        Arrays.asList(BigDecimal.ONE), objUnderTest.list(), "Objects are not listed right");
    assertTrue(objUnderTest.undo());
    assertEquals(0, objUnderTest.size(), "Should have no elements");
    assertEquals(Arrays.asList(), objUnderTest.list(), "Objects are not listed right");
    assertFalse(objUnderTest.undo()); // No more actions to undo; Negating case
  }

}
