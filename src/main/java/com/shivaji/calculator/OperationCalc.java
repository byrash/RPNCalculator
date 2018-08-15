package com.shivaji.calculator;

import static com.shivaji.util.BigDecimalSqrtUtil.bigSqrt;
import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * static utility to do the real calculation of the Operation Supplied
 *
 * @author Shivaji
 */
public class OperationCalc {

  private OperationCalc() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(OperationCalc.class);

  // At least 15 scale is required as per requirement
  public static final int SCALE = 15;

  /**
   * Overloaded method for conveniently passing just a single argument for an operation. Basically
   * should be used only in SQRT action
   *
   * @param op
   * @param item
   * @return
   */
  public static BigDecimal doCalc(Operation op, BigDecimal item) {
    if (op != null && !op.equals(Operation.SQRT)) {
      throw new IllegalArgumentException("Only SQRT operation takes single argument");
    }
    return OperationCalc.doCalc(op, item, null);
  }

  /**
   * Performs calculation based on operation.
   *
   * <p>Undo or clear should be not passed to this guy as theya re not calculations
   *
   * @param op
   * @param lhs
   * @param rhs
   * @return
   */
  public static BigDecimal doCalc(Operation op, BigDecimal lhs, BigDecimal rhs) {
    LOG.debug("Calculating for Operation [{}] with inputs {} and {}", op, lhs, rhs);
    switch (op) {
      case ADD:
        return setScale(lhs.add(rhs));
      case SQRT:
        return setScale(bigSqrt(lhs));
      case DIV:
        return rhs.divide(lhs, SCALE, HALF_UP);
      case MUL:
        return setScale(lhs.multiply(rhs));
      case SUB:
        return setScale(rhs.subtract(lhs));
      default:
        throw new IllegalArgumentException("Un Acceptable Operation [" + op + "]");
    }
  }

  /**
   * Sets default scale for the result being produced after math
   *
   * @param obj
   * @return
   */
  private static BigDecimal setScale(BigDecimal obj) {
    return obj.setScale(SCALE, HALF_UP);
  }
}
