package com.shivaji;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class SolutionTest {

  @Test
  void solution() {
    Solution objectUnderTest = new Solution();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setErr(new PrintStream(outContent));
    assertEquals(-1, objectUnderTest.solution("5 6 + -"), "Should return -1");
    assertTrue(
        outContent.toString().contains("Insufficient elements on stack to perform operation"));
    assertEquals(-1, objectUnderTest.solution("3 DUP 5 - -"), "Should return -1");
    assertTrue(outContent.toString().contains("Operation resulted in negative value"));
    assertEquals(7, objectUnderTest.solution("13 DUP 4 POP 5 DUP + DUP + -"), "Should return 7");
  }
}
