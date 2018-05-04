package com.shivaji;

import static com.shivaji.util.RPNUtil.parseAndCleanseInputStr;
import static org.apache.commons.lang3.StringUtils.SPACE;

import com.shivaji.calculator.Operation;
import com.shivaji.calculator.OperationCalc;
import com.shivaji.datastructure.RPNStack;
import com.shivaji.util.RPNUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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

    private final RPNStack<BigDecimal> stack;

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
        RPNCalculator rpnCalculator = new RPNCalculator(new RPNStack<>());
        while (!isExit(input)) {
            input = scan.nextLine();
            String resultStack = rpnCalculator.calculate(input);
            LOG.info("Stack: {}", resultStack);
        }
    }

    private static boolean isExit(String input) {
        return StringUtils.equalsIgnoreCase(input, "exit");
    }

    public RPNCalculator(RPNStack<BigDecimal> stack) {
        this.stack = stack;
    }

    public String calculate(String inputStr) {
        if (isExit(inputStr)) {
            return getStackForDisplay();
        }
        List<String> tokens = parseAndCleanseInputStr(inputStr);
        LOG.info("Input considered after cleansing --> {}", StringUtils.join(tokens, SPACE));
        boolean continueWithNextItem = Boolean.TRUE;

        // Using legacy loop to mitigate with breaking the flow
        // if any operation is not possible
        // Throwing exception is expensive within forEach
        for (int index = 0; index < tokens.size(); index++) {

            if (!continueWithNextItem) {
                break;
            }

            String token = tokens.get(index);

            if (NumberUtils.isCreatable(token)) { // Number
                stack.pushWithStashing(Optional.of(new BigDecimal(token)));
            }
            else { // Operation
                Optional<Operation> parse = Operation.parse(token);
                // No Point is checking is present, as cleansing has confirmed this
                switch (parse.get()) {
                    case UNDO:
                        continueWithNextItem = undo(continueWithNextItem, index);
                        break;
                    case CLEAR:
                        stack.stashAndclear(); // Clear is always possible
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

        return getStackForDisplay();
    }

    private boolean mathOperation(int index, Operation operation) {
        boolean isSqrtOperation = operation.equals(Operation.SQRT);
        if (stack.hasAtleastTwoItems() || (isSqrtOperation && stack.hasAtleastOneItem())) {
            stack.stash();
            stack.pushWithoutStashing(
                Optional.of(
                    OperationCalc.doCalc(
                        operation,
                        stack.popWithOutStashing().get(),
                        isSqrtOperation ? null : stack.popWithOutStashing().get())));
            return Boolean.TRUE;
        }
        inSufficientParametersMsg(operation.getVal(), index);
        return Boolean.FALSE;
    }

    private boolean undo(boolean continueWithNextItem, int index) {
        boolean isUndoSuccess = stack.undo();
        if (!isUndoSuccess) {
            inSufficientParametersMsg(Operation.UNDO.getVal(), index);
            continueWithNextItem = false;
        }
        return continueWithNextItem;
    }

    private String getStackForDisplay() {
        List<String> stackList =
            stack
                .list()
                .stream()
                .map(RPNUtil.bigDecimalWith10DecimalPlacesStr)
                .collect(Collectors.toList());
        return StringUtils.join(stackList, SPACE);
    }

    private void inSufficientParametersMsg(String operation, int index) {
        int position = (index + 1) * 2 - 1; // considering spaces between operations as well
        //    int position = ( index + 1); // This is ideal position of opeartion however in problem
        // statement example it sounded like they wanted above as there in a ambigeous requirement
        // statement about  position

        LOG.warn("operator {} (position: {}): insufficient parameters", operation, position);
    }
}
