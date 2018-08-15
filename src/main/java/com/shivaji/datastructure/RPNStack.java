package com.shivaji.datastructure;

import static com.shivaji.calculator.OperationCalc.doCalc;

import com.shivaji.calculator.Operation;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * A Generic wrapper on top of Array Deque Implementation to support stack operations whilst making
 * stack immutable as requirments demand
 *
 * <p>1) Using optionals to safe guard myself from NPE 2) Handles all Runtime Exceptions raised from
 * stack actions, if they have to
 *
 * <p>** All the operations are immutable on this implementation unless explicitly asked so by the
 * user **
 *
 * @author Shivaji
 */
public class RPNStack {

  // Ensuring thread safe i.e read consistency just in case this object is shared in a threaded
  // environment.
  // Volatile keyword is my life saver here for worst case threaded scenario.
  private volatile Deque<RPNActivityVo> rpnActivityStack;
  private volatile Deque<BigDecimal> stack;

  /** constructors intis the stacks */
  public RPNStack() {
    super();
    // Mutating the sate of stack per instance to support multiple actions on the stack
    this.rpnActivityStack = new ArrayDeque<>();
    this.stack = new ArrayDeque<>();
  }

  /**
   * push an element to stack
   *
   * @param objToPush
   */
  public void push(Optional<BigDecimal> objToPush) {
    if (objToPush.isPresent()) {
      BigDecimal value = objToPush.get();
      RPNActivityVo rpnActivityVo = new RPNActivityVo();
      this.rpnActivityStack.push(rpnActivityVo);
      this.stack.push(value);
    }
  }

  /**
   * Pops an element from the stack without stashing the state
   *
   * @return
   */
  public void performOperation(Operation operation) {
    boolean isSqrtOperation = operation.equals(Operation.SQRT);
    RPNActivityVo rpnActivityVo = new RPNActivityVo();
    if (isSqrtOperation) {
      BigDecimal pop = this.stack.pop();
      rpnActivityVo.getValues().add(pop);
      rpnActivityVo.setOperation(operation);
      this.rpnActivityStack.push(rpnActivityVo);
      this.stack.push(doCalc(operation, pop, null));
    } else {
      BigDecimal lhs = this.stack.pop();
      BigDecimal rhs = this.stack.pop();
      rpnActivityVo.getValues().add(rhs);
      rpnActivityVo.getValues().add(lhs);
      rpnActivityVo.setOperation(operation);
      this.rpnActivityStack.push(rpnActivityVo);
      this.stack.push(doCalc(operation, lhs, rhs));
    }
  }

  public boolean isEmpty() {
    return this.stack.isEmpty();
  }

  /**
   * List all items on the current stack
   *
   * @return
   */
  public List<BigDecimal> list() {
    List<BigDecimal> items = new ArrayList<>(this.size());
    this.stack.iterator().forEachRemaining(items::add);
    Collections.reverse(items);
    return items;
  }

  /**
   * Returns the last element of the queue without removing it
   *
   * @return
   */
  public Optional<BigDecimal> peak() {
    if (!this.isEmpty()) {
      return Optional.of(this.stack.getFirst());
    }
    return Optional.empty();
  }

  /**
   * Returns Current Stack Size
   *
   * @return
   */
  public int size() {
    return this.stack.size();
  }

  /** Clears everything off the queue */
  public void clear() {
    if (this.stack.isEmpty()) {
      return;
    }
    RPNActivityVo rpnActivityVo = new RPNActivityVo();
    rpnActivityVo.getValues().addAll(list());
    this.rpnActivityStack.push(rpnActivityVo);
    this.stack.clear();
  }

  /**
   * Undo the previous action and return if undo is success or not with a boolean indicator true or
   * false
   *
   * @return
   */
  public boolean undo() {
    if (this.rpnActivityStack.isEmpty()) {
      return false; // Nothing to remove
    }
    this.stack.pop();
    RPNActivityVo rpnActivityVo = this.rpnActivityStack.pop();
    rpnActivityVo.getValues().forEach(item -> this.stack.push(item));
    return true;
  }

  public boolean hasAtleastTwoItems() {
    return this.size() >= 2;
  }

  public boolean hasAtleastOneItem() {
    return this.size() >= 1;
  }
}
