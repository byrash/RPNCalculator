package com.shivaji.datastructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class RPNStack<T> {

  private static final Logger LOG = LoggerFactory.getLogger(RPNStack.class);

  // Ensuring thread safe i.e read consistency just in case this object is shared in a threaded
  // environment.
  // Volatile keyword is my life saver here for worst case threaded scenario.
  private volatile Deque<Deque<T>> _stackStashes;

  /** constructors intis the stacks */
  public RPNStack() {
    super();
    // Mutating the sate of stack per instance to support multiple actions on the stack
    this._stackStashes = new ArrayDeque<>(); // Stack stash holder is again a stack by itself
    this._stackStashes.push(new ArrayDeque<>()); // Bootstrapping with an empty stack on stack stash
  }

  /**
   * Returns teh current stack that is being used from stackStash i.e the latest on stack stash
   *
   * @return
   */
  private Deque<T> getCurrentStack() {
    return this._stackStashes.peek();
  }

  /**
   * Stashes the current state of current stack item and pushes a new stack with a clone of current
   * stack to work on
   *
   * @return
   */
  private Deque<T> stashAndGetNewStack() {
    this._stackStashes.push(((ArrayDeque<T>) getCurrentStack()).clone());
    return getCurrentStack();
  }

  /** Polished API for external usage with internally use <link>stashAndGetNewStack</link> */
  public void stash() {
    stashAndGetNewStack();
  }

  /**
   * push an element to stack
   *
   * @param objToPush
   */
  public void pushWithStashing(Optional<T> objToPush) {
    LOG.debug("Pushing with Stashing");
    if (objToPush.isPresent()) {
      stashAndGetNewStack().push(objToPush.get());
    }
    // I dont care if nothing is supplied
    // Moreover I dont expect a null to be passed to push onto stack as it should be valid math
    // action or value
  }

  /**
   * push an element to stack without stashing the state
   *
   * @param objToPush
   */
  public void pushWithoutStashing(Optional<T> objToPush) {
    LOG.debug("Pushing with out Stashing");
    if (objToPush.isPresent()) {
      getCurrentStack().push(objToPush.get());
    }
    // I dont care if nothing is supplied
    // Moreover I dont expect a null to be passed to push onto stack as it should be valid math
    // action or value
  }

  /**
   * Pops an element from the stack without stashing the state
   *
   * @return
   */
  public Optional<T> popWithOutStashing() {
    LOG.debug("Poping with Out Stashing");
    // Only if stack is not empty
    if (!this.isEmpty()) {
      return Optional.of(getCurrentStack().pop());
    }
    // Nothing to pop off. NPE saver Optional
    return Optional.empty();
  }

  /**
   * Pops an element from the stack
   *
   * @return
   */
  public Optional<T> popWithStashing() {
    LOG.debug("Poping with Stashing");
    // Only if stack is not empty
    if (!this.isEmpty()) {
      return Optional.of(stashAndGetNewStack().pop());
    }
    // Nothing to pop off. NPE saver Optional
    return Optional.empty();
  }

  public boolean isEmpty() {
    return getCurrentStack().isEmpty();
  }

  /**
   * List all items on the current stack
   *
   * @return
   */
  public List<T> list() {
    List<T> items = new ArrayList<>(this.size());
    getCurrentStack().iterator().forEachRemaining(items::add);
    Collections.reverse(items);
    return items;
  }

  /**
   * Returns the last element of the queue without removing it
   *
   * @return
   */
  public Optional<T> peek() {
    if (!this.isEmpty()) {
      return Optional.of(getCurrentStack().getFirst());
    }
    return Optional.empty();
  }

  /**
   * Returns Current Stack Size
   *
   * @return
   */
  public int size() {
    return getCurrentStack().size();
  }

  /** Clears everything off the queue */
  public void stashAndclear() {
    stashAndGetNewStack().clear();
  }

  /**
   * Undo the previous action and return if undo is success or not with a boolean indicator true or
   * false
   *
   * @return
   */
  public boolean undo() {
    // We insert initial stack to bootstrap and so undo is possible only for action latter boot
    // strapping
    if (this._stackStashes.size() <= 1) {
      return false;
    }
    this._stackStashes.pop();
    return true;
  }

  public boolean hasAtleastTwoItems() {
    return this.size() >= 2;
  }

  public boolean hasAtleastOneItem() {
    return this.size() >= 1;
  }
}
