package com.shivaji.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

/** @author Shivaji */
class BigDecimalSqrtUtilTest {

  @Test
  void bigSqrt() {
    assertEquals(
        "24.738633753705963",
        BigDecimalSqrtUtil.bigSqrt(BigDecimal.valueOf(612)).setScale(15,RoundingMode.HALF_UP).toPlainString(),
            "Expected 24.738633753705963");
  }
}
