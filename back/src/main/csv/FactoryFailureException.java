package csv;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the FactoryFailureException class, which extends the Exception class. It is an exception
 * to be thrown when there is an error in the create method from a CreatorFromRow class. It takes in
 * a String message to explain the issue, and the row that caused the issue.
 */
public class FactoryFailureException extends Exception {

  /** Row instance variable stores the row that caused the FactoryFailureException. */
  final List<String> row;

  /**
   * This is the constructor for the FactoryFailureException class. It takes in a string
   * representing the error message for the exception, as well as a list of strings representing the
   * row that cause the exception. It stores the row as an instance variable.
   *
   * @param message the message explaining why the exception was thrown
   * @param row the row, as a list of strings, that caused the exception to be thrown
   */
  public FactoryFailureException(String message, List<String> row) {
    super(message);
    this.row = new ArrayList<>(row);
  }
}
