package com.shivaji.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

/** @author Shivaji */
class OperationTest {

  @Test
  void parse_add() {
    assertEquals(Optional.of(Operation.ADD), Operation.parse("+"), "Should be add");
  }

  @Test
  void parse_okayWithNull() {
    // Should be null safe
    assertEquals(Optional.empty(), Operation.parse(null), "Should be Okay with null");
  }

  @Test
  void parse_sub() {
    assertEquals(Optional.of(Operation.SUB), Operation.parse("-"), "Should be sub");
  }

  @Test
  void parse_junk() {
    assertEquals(Optional.empty(), Operation.parse("some junk"), "Should be okay with junk value");
  }

  @Test
  void parse_sqrt() {
    assertEquals(Optional.of(Operation.SQRT), Operation.parse("sqrt"), "Should be sqrt");
    // Case should not matter
    assertEquals(Optional.of(Operation.SQRT), Operation.parse("SQRT"), "Should be sqrt");
  }

  @Test
  void parse_clear() {
    assertEquals(Optional.of(Operation.CLEAR), Operation.parse("clear"), "Should be clear");
    // Case should not matter
    assertEquals(Optional.of(Operation.CLEAR), Operation.parse("ClEaR"), "Should be clear");
  }

  @Test
  void parse_div() {

    assertEquals(Optional.of(Operation.DIV), Operation.parse("/"), "Should be add");
  }

  @Test
  void parse_mul() {
    assertEquals(Optional.of(Operation.MUL), Operation.parse("*"), "Should be add");
  }

  @Test
  void parse_undo() {
    assertEquals(Optional.of(Operation.UNDO), Operation.parse("undo"), "Should be add");
  }
}
