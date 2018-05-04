package com.shivaji.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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

  private RPNStack<BigDecimal> objUnderTest;

  @BeforeEach
  void setUp() {
    // New object for every operation
    objUnderTest = new RPNStack<>();
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.ONE));
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.ZERO));
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.TEN));
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
  void pop() {
    assertEquals(
        Optional.of(BigDecimal.TEN), objUnderTest.popWithStashing(), "Should be big decimal 10.");
    assertEquals(
        Optional.of(BigDecimal.ZERO), objUnderTest.popWithStashing(), "Should be big decimal 0. ");
    assertEquals(
        Optional.of(BigDecimal.ONE), objUnderTest.popWithStashing(), "Should be big decimal 1.");
    // Negating case
    assertFalse(objUnderTest.popWithStashing().isPresent(), "No object should exists");
  }

  @Test
  void isEmpty() {
    assertFalse(objUnderTest.isEmpty(), "Cant be empty");
    objUnderTest.stashAndclear();
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
    assertEquals(Optional.of(BigDecimal.TEN), objUnderTest.peek(), "Last item should 10");
    assertEquals(3, objUnderTest.size(), "Peek should not have removed item");
  }

  @Test
  void size() {
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.ONE));
    assertEquals(4, objUnderTest.size(), "Should have 4 items");
  }

  @Test
  void clear() {
    assertEquals(3, objUnderTest.size(), "Should have 3 items");
    objUnderTest.stashAndclear();
    assertEquals(0, objUnderTest.size(), "Should have 0 items");
  }

  /** Tests all scenarios on Stack */
  @Test
  void stateRetentionTest() {
    assertEquals(3, objUnderTest.size(), "Should have 3 elements");
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.ONE));
    assertEquals(4, objUnderTest.size(), "Should have 4 elements");
    objUnderTest.pushWithStashing(Optional.of(BigDecimal.ONE));
    assertEquals(5, objUnderTest.size(), "Should have 5 elements");
    objUnderTest.popWithStashing();
    assertEquals(4, objUnderTest.size(), "Should have 4 elements");
    objUnderTest.peek();
    assertEquals(4, objUnderTest.size(), "Should have 4 elements");
    objUnderTest.stashAndclear();
    assertEquals(0, objUnderTest.size(), "Should have 0 elements");
    assertEquals(Collections.EMPTY_LIST, objUnderTest.list(), "List should be empty");
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

  @Test
  void hasAtleastTwoItems() {
    assertTrue(objUnderTest.hasAtleastTwoItems(), "Should Have more than 2 items");
    objUnderTest.popWithStashing();
    objUnderTest.popWithStashing();
    assertFalse(objUnderTest.hasAtleastTwoItems(), "Should Have less than 2 items");
  }

  @Test
  void hasAtleastTwoItem() {
    assertTrue(objUnderTest.hasAtleastOneItem(), "Should Have more than 1 items");
    objUnderTest.popWithStashing();
    objUnderTest.popWithStashing();
    objUnderTest.popWithStashing();
    assertFalse(objUnderTest.hasAtleastOneItem(), "Should Have less than 1 items");
  }

  @Test
  void popWithOutStashing() {
    assertEquals(3, objUnderTest.size(), "Should have 3 elements");
    assertEquals(
        Optional.of(BigDecimal.TEN),
        objUnderTest.popWithOutStashing(),
        "Should be big decimal 10.");
    assertEquals(
        Optional.of(BigDecimal.ZERO),
        objUnderTest.popWithOutStashing(),
        "Should be big decimal 0. ");
    assertEquals(
        Optional.of(BigDecimal.ONE), objUnderTest.popWithOutStashing(), "Should be big decimal 1.");
    objUnderTest.undo();
    assertEquals(2, objUnderTest.size(), "Should be tow");
    // Should have previous to 10 push state as all operation later are not stashed
    assertEquals("[1, 0]", objUnderTest.list().toString(), "Should have only 1 and 0");
  }

  @Test
  void pushWithOutStashing() {
    assertEquals(3, objUnderTest.size(), "Should have 3 elements");
    objUnderTest.pushWithoutStashing(Optional.of(BigDecimal.ONE));
    objUnderTest.pushWithoutStashing(Optional.of(BigDecimal.ZERO));
    objUnderTest.pushWithoutStashing(Optional.of(BigDecimal.TEN));
    assertEquals(6, objUnderTest.size(), "Should have 6 elements");
    objUnderTest.undo();
    // Should be only 2 as 1,0,10 state is not stash all stash has is 1,0
    assertEquals(2, objUnderTest.size(), "Should be two");
    // As ignoring all later push in stashing process with should get init state
    assertEquals("[1, 0]", objUnderTest.list().toString(), "Should have only 1 and 0");
  }
}
