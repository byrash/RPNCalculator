package com.shivaji;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * @author Shivaji Byrapaneni
 */
public class Solution {

  public int solution(String S) {
    String[] split = S.split("\\s");
    Deque<Integer> stack = new ArrayDeque<>();
    for (String str : split) {
      try {
        try {
          int anInt = Integer.parseInt(str);
          invalidIntCheck(anInt);
          stack.push(anInt);
        } catch (NumberFormatException ne) {
          Operation operation = Operation.parse(str);
          switch (operation) {
            case DUP:
              stack.push(stack.getFirst());
              break;
            case POP:
              stack.pop();
              break;
            case PLUS:
              Integer lhsForAdd = stack.pop();
              Integer rhsForAdd = stack.pop();
              int add = lhsForAdd + rhsForAdd;
              invalidIntCheck(add);
              stack.push(add);
              break;
            case MINUS:
              Integer lhsForSub = stack.pop();
              Integer rhsForSub = stack.pop();
              int sub = lhsForSub - rhsForSub;
              invalidIntCheck(sub);
              stack.push(sub);
              break;
          }
        }
      } catch (NoSuchElementException e) {
        System.err.println("Insufficient elements on stack to perform operation.");
        return -1;
      } catch (InvalidValueException e) {
        System.err.println("Operation resulted in negative value");
        return -1;
      } catch (RuntimeException e) {
        System.err.println(e.getMessage());
        return -1;
      }
    }
    if (stack.isEmpty()) {
      System.err.println("Operation resulted in negative value");
      return -1;
    }
    return stack.getFirst();
  }

  private void invalidIntCheck(int anInt) throws InvalidValueException {
    // 1048575 == (2 ^ 20) -1
    if (anInt < 0 || anInt > 1048575) {
      throw new InvalidValueException();
    }
  }

  public class InvalidValueException extends Exception {

  }

  public static void main(String[] args) {
    Solution obj = new Solution();
    // 13 DUP 4 POP 5 DUP + DUP + -
    System.out.println(obj.solution("5 6 + -"));
    System.out.println(obj.solution("3 DUP 5 - -"));
    System.out.println(obj.solution("13 DUP 4 POP 5 DUP + DUP + -"));
  }

  private enum Operation {
    PUSH("PUSH"),
    POP("POP"),
    DUP("DUP"),
    PLUS("+"),
    MINUS("-");
    String val;

    Operation(String val) {
      this.val = val;
    }

    public static Operation parse(String str) {
      for (Operation op : Operation.values()) {
        if (op.getVal().equals(str)) {
          return op;
        }
      }
      throw new RuntimeException("Invalid Operation");
    }

    public String getVal() {
      return val;
    }
  }
}
