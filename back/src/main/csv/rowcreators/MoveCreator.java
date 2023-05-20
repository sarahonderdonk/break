package csv.rowcreators;

import csv.FactoryFailureException;
import csv.rowobjects.Move;

import java.util.List;

/**
 * This is the StarCreator class, which implements CreatorFromRow using a Star as the type to be
 * made from the rows. It defines a create method that returns a given row as a list of stars, using
 * data from the row to initialize the star's five properties: its ID, name, x-location, y-location,
 * and z-location.
 *
 * @author Pauline Nguyen
 * @version February 3, 2023
 */
public class MoveCreator implements CreatorFromRow<Move> {

  /** This is the constructor for the StarCreator class. It does not store any information. */
  public MoveCreator() {}

  /**
   * This method takes in a row as a list of strings, converts it to a Star object, and returns that
   * Star. It throws a FactoryFailureException if the row does not have enough values, or if the
   * datatype of the values are incompatible for a Star.
   *
   * @param row a list of strings representing the row to be made into object
   * @return a Star created using the row's data
   * @throws FactoryFailureException if the row does not have the right amount of values, or if the
   *     datatype of the values are incompatible for a Star
   */
  @Override
  public Move create(List<String> row) throws FactoryFailureException {
    return new Move(
        Integer.parseInt(row.get(0)),
        row.get(1),
        (row.get(2)),
        Integer.parseInt(row.get(3)),
        (row.get(4))
    );
  }

  /**
   * This is a helper method that determines whether a string input can be made into an integer.
   *
   * @param input the string to be determined as an integer or not an integer
   * @return true if the string can be made into an integer, and false otherwise
   */
  private boolean isInt(String input) {
    try {
      Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * This is a helper method that determines whether a string input can be made into a float.
   *
   * @param input the string to be determined as a float or not a float
   * @return true if the string can be made into a float, and false otherwise
   */
  private boolean isFloat(String input) {
    try {
      Float.parseFloat(input);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
