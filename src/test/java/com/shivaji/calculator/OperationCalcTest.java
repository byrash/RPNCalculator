package com.shivaji.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** @author Shivaji */
class OperationCalcTest {

  @Test
  void doCalc_sqrt() {
    assertEquals(
        "24.738633753705963",
        OperationCalc.doCalc(Operation.SQRT, new BigDecimal(612)).toPlainString(),
        "Expected sqrt of 612 to eb 24.738633753705963");
  }

  @Test
  void doCalc_add() {
    assertEquals(
        "2.200000000000000",
        OperationCalc.doCalc(Operation.ADD, new BigDecimal(1.0), new BigDecimal(1.2))
            .toPlainString(),
        "Expected 2.200000000000000");
  }

  @Test
  void doCalc_sub() {
    assertEquals(
        "0.200000000000000",
        OperationCalc.doCalc(Operation.SUB, new BigDecimal(1.0), new BigDecimal(1.2))
            .toPlainString(),
        "Expected 0.200000000000000");
  }

  @Test
  void doCalc_mul() {
    assertEquals(
        "1.200000000000000",
        OperationCalc.doCalc(Operation.MUL, new BigDecimal(1.0), new BigDecimal(1.2))
            .toPlainString(),
        "Expected 1.200000000000000");
  }

  @Test
  void doCalc_div() {
    assertEquals(
        "1.200000000000000",
        OperationCalc.doCalc(Operation.DIV, new BigDecimal(1.0), new BigDecimal(1.2))
            .toPlainString(),
        "Expected 1.200000000000000");
  }

  @Test
  void doCalc_undoFails() {
    assertThrows(
        IllegalArgumentException.class, () -> OperationCalc.doCalc(Operation.UNDO, null));
  }

  @Test
  void doCalc_addWithOneParams() {
    assertThrows(
        IllegalArgumentException.class, () -> OperationCalc.doCalc(Operation.ADD, new BigDecimal(1.0)));
  }
}
