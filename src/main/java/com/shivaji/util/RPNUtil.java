package com.shivaji.util;

import com.shivaji.calculator.Operation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Utility class used for calculations
 *
 * @author Shivaji
 */
public class RPNUtil {

  /** Decides if the given token can be parsed to a number or operation we accept */
  private static final Predicate<String> isParsableToNumberOrAcceptableOperation =
      item -> NumberUtils.isCreatable(item) || Operation.parse(item).isPresent();
  /** This supports the requirment to print only up to 10 decimals when printing stack */
  public static final Function<BigDecimal, String> bigDecimalWith10DecimalPlacesStr =
      obj -> obj.setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString();

  /**
   * Parses and asserts the input string is valid. Removes any junk chars
   *
   * @param inputStr
   * @return
   */
  public static List<String> parseAndCleanseInputStr(String inputStr) {
    return Arrays.stream(StringUtils.split(inputStr.trim()))
        .filter(isParsableToNumberOrAcceptableOperation)
        .collect(Collectors.toList());
  }
}
