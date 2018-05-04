package com.shivaji.calculator;

import java.util.EnumSet;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/** Enumerates all the possible or acceptable operations and parses the operation supplied */
public enum Operation {
  // +, -, *, /, sqrt, undo, clear
  ADD("+"),
  SUB("-"),
  MUL("*"),
  DIV("/"),
  SQRT("sqrt"),
  UNDO("undo"),
  CLEAR("clear");

  private String val;

  Operation(String val) {
    this.val = val;
  }

  public static Optional<Operation> parse(String str) {
    return EnumSet.allOf(Operation.class)
        .stream()
        .filter(op -> StringUtils.equalsAnyIgnoreCase(op.getVal(), str))
        .findAny();
  }

  public String getVal() {
    return val;
  }
}
