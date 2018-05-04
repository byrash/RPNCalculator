**Problem Statement & Expectation**

_**Requirements**_

• The calculator has a stack that can contain real numbers.
• The calculator waits for user input and expects to receive strings containing
whitespace separated lists of numbers and operators.
• Numbers are pushed on to the stack. Operators operate on numbers that are on
the stack.
• Available operators are +, -, *, /, sqrt, undo, clear
• Operators pop their parameters off the stack, and push their results back onto
the stack.
• The ‘clear’ operator removes all items from the stack.
• The ‘undo’ operator undoes the previous operation. “undo undo” will undo the
previo us two operations.
• sqrt performs a square root on the top item from the stack
• The ‘+’, ‘-’, ‘*’, ‘/’ operators perform addition, subtraction, multiplication and
division respectively on the top two items from the
• stack.
• After processing an input string, the calculator displays the current contents of the stack as a space-separated list.
• Numbers should be stored on the stack to at least 15 decimal places of precision, but displayed to 10 decimal places (or less if it causes no loss of precision).
• All numbers should be formatted as plain decimal strings (ie. no engineering formatting).
• If an operator cannot find a sufficient number of parameters on the stack, a warning is displayed:
operator <operator> (position: <pos>): insufficient parameters
• After displaying the warning, all further processing of the string terminates and the current state of the stack is displayed.

_**Deliverables**_

• The solution submitted should include structure, source code, configuration and any tests or test code you deem necessary - no need
• to package class files.
• Solve the problem in Java, C# or in a specific language that you may have been
directed to use.
• Solve the problem as though it were “production level” code. • It is not required to provide any graphical interface.
In order to get around firewall issues we recommend the solution be packaged as a password protected zip file.

**_Tech Stack & Frameworks Used_**

1) Java 1.8
2) Junit 5
3) apache commons lang 3
4) slf4j via logback for logging
5) Maven

_This solution was developed using TDD approach._


**_How to run ??_**
```text
git clone https://github.com/byrash/RPNCalculator.git ./RPNCalculator

cd RPNCalculator

mvn clean install

** Maven produces a self executable jar, you 
could run with multiple options as below **

java -jar target/rpn-calculator-1.0-SNAPSHOT.jar 

or

double click rpn-calculator-1.0-SNAPSHOT.jar file produced in target folder 
( Assuming you jar launcher is set to run jar files )

```



