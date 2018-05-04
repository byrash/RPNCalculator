package com.shivaji.calculator;

import com.shivaji.util.BigDecimalSqrtUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * static utility to do the real calculation of the Operation Supplied
 *
 * @author Shivaji
 */
public class OperationCalc {

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
   * @param item1
   * @param item2
   * @return
   */
  public static BigDecimal doCalc(Operation op, BigDecimal item1, BigDecimal item2) {
    LOG.debug("Calculating for Operation [{}] with inputs {} and {}", op, item1, item2);
    switch (op) {
      case ADD:
        return setScale(item1.add(item2));
      case SQRT:
        return setScale(BigDecimalSqrtUtil.bigSqrt(item1));
      case DIV:
        return item2.divide(item1, SCALE, RoundingMode.HALF_UP);
      case MUL:
        return setScale(item1.multiply(item2));
      case SUB:
        return setScale(item2.subtract(item1));
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
    return obj.setScale(SCALE, RoundingMode.HALF_UP);
  }
}
