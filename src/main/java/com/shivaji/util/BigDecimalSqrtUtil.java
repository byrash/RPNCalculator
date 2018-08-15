package com.shivaji.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Lifted from
 * https://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java/19743026
 *
 * <p>Using http://en.wikipedia.org/wiki/Newton's_method#Examples process
 *
 * @author Shivaji
 */
public class BigDecimalSqrtUtil {

  private BigDecimalSqrtUtil() {
  }

  private static final BigDecimal SQRT_DIG = BigDecimal.valueOf(150);
  private static final BigDecimal SQRT_PRE = BigDecimal.valueOf(10).pow(SQRT_DIG.intValue());

  /**
   * Private utility method used to compute the square root of a BigDecimal.
   *
   * @author Luciano Culacciatti
   * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
   */
  private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
    BigDecimal fx = xn.pow(2).add(c.negate());
    BigDecimal fpx = xn.multiply(new BigDecimal(2));
    BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
    xn1 = xn.add(xn1.negate());
    BigDecimal currentSquare = xn1.pow(2);
    BigDecimal currentPrecision = currentSquare.subtract(c);
    currentPrecision = currentPrecision.abs();
    if (currentPrecision.compareTo(precision) <= -1) {
      return xn1;
    }
    return sqrtNewtonRaphson(c, xn1, precision);
  }

  /**
   * Uses Newton Raphson to compute the square root of a BigDecimal.
   *
   * @author Luciano Culacciatti
   * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
   */
  public static BigDecimal bigSqrt(BigDecimal c) {
    return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
  }
}
