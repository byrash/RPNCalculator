package com.shivaji;

import static com.shivaji.util.RPNUtil.parseAndCleanseInputStr;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Optional.of;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import com.shivaji.calculator.Operation;
import com.shivaji.datastructure.RPNStack;
import com.shivaji.util.RPNUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main java class method to bootstrap the application.
 *
 * <p>This class accepts inputs and does teh calculation
 *
 * @author Shivaji
 */
public class RPNCalculator {

  private static final Logger LOG = LoggerFactory.getLogger(RPNCalculator.class);

  private final RPNStack rpnStack;

  public static void main(String[] args) {
    LOG.info(SPACE);
    LOG.info("<!---------------------------------------------!>");
    LOG.info("Use exit and hit enter to exit the program");
    LOG.info("Enter any real number with valid operation +, -, *, /, sqrt, undo, clear");
    LOG.info("No Real and not acceptable operations are ignored.");
    LOG.info("<!---------------------------------------------!>");
    LOG.info(SPACE);
    Scanner scan = new Scanner(System.in);
    String input = null;
    RPNCalculator rpnCalculator = new RPNCalculator(new RPNStack());
    while (!isExit(input)) {
      input = scan.nextLine();
      String resultStack = rpnCalculator.calculate(input);
      LOG.info("Stack: {}", resultStack);
    }
  }

  private static boolean isExit(String input) {
    return equalsIgnoreCase(input, "exit");
  }

  RPNCalculator(RPNStack rpnStack) {
    this.rpnStack = rpnStack;
  }

  public String calculate(String inputStr) {
    if (isExit(inputStr)) {
      return getStackForDisplay();
    }
    List<String> tokens = parseAndCleanseInputStr(inputStr);
    LOG.info("Input considered after cleansing --> {}", tokens.stream().collect(joining(SPACE)));
    boolean continueWithNextItem = TRUE;

    // Using legacy loop to mitigate with breaking the flow
    // if any operation is not possible
    // Throwing exception is expensive within forEach
    for (int index = 0; index < tokens.size(); index++) {

      if (!continueWithNextItem) {
        break;
      }

      String token = tokens.get(index);

      if (NumberUtils.isCreatable(token)) { // Number
        rpnStack.push(of(new BigDecimal(token)));
      } else { // Operation
        Optional<Operation> parse = Operation.parse(token);
        if (parse.isPresent()) {
          // No Point is checking is present, as cleansing has confirmed this
          switch (parse.get()) {
            case UNDO:
              continueWithNextItem = undo(index);
              break;
            case CLEAR:
              rpnStack.clear(); // Clear is always possible
              break;
            case ADD:
              continueWithNextItem = mathOperation(index, Operation.ADD);
              break;
            case SUB:
              continueWithNextItem = mathOperation(index, Operation.SUB);
              break;
            case MUL:
              continueWithNextItem = mathOperation(index, Operation.MUL);
              break;
            case DIV:
              continueWithNextItem = mathOperation(index, Operation.DIV);
              break;
            case SQRT:
              continueWithNextItem = mathOperation(index, Operation.SQRT);
              break;
          }
        }
      }
    }

    return getStackForDisplay();
  }

  private boolean mathOperation(int index, Operation operation) {
    boolean isSqrtOperation = operation.equals(Operation.SQRT);
    if (rpnStack.hasAtleastTwoItems() || (isSqrtOperation && rpnStack.hasAtleastOneItem())) {
      rpnStack.performOperation(operation);
      return TRUE;
    }
    inSufficientParametersMsg(operation.getVal(), index);
    return FALSE;
  }

  private boolean undo(int index) {
    boolean isUndoSuccess = rpnStack.undo();
    if (!isUndoSuccess) {
      inSufficientParametersMsg(Operation.UNDO.getVal(), index);
      return FALSE;
    }
    return TRUE;
  }

  private String getStackForDisplay() {
    return rpnStack
        .list()
        .stream()
        .map(RPNUtil.bigDecimalWith10DecimalPlacesStr)
        .collect(joining(SPACE));
  }

  private void inSufficientParametersMsg(String operation, int index) {
    int position = (index + 1) * 2 - 1; // considering spaces between operations as well
    //    int position = ( index + 1); // This is ideal position of opeartion however in problem
    // statement example it sounded like they wanted above as there in a ambigeous requirement
    // statement about  position
    LOG.warn("operator {} (position: {}): insufficient parameters", operation, position);
  }
}
