package csv;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Searcher class. It takes in a String value to search for and a boolean indicating
 * whether a CSV has a header. It can search a list of lists containing strings (from a parsed CSV),
 * either by calling colSearch to search within a specific column, or calling regSearch to search
 * all columns. If the value is found, a list of integers is returned, with each integer
 * representing the row index the value was found.
 */
public class Searcher {

  /**
   * Instance variables for Searcher keep track of the string to be searched for, as well as the
   * boolean indicating if a CSV has a header.
   */
  private final String value;


  /**
   * This is the constructor for the Searcher class. It takes in a String value that will be
   * searched for in a parsed CSV, as well as a boolean indicating if a CSV has a header, both of
   * which are stored as instance variables.
   *
   * @param val the string to be searched for
   */
  public Searcher(String val) {
    this.value = val;
  }

  /**
   * This method searches for a given value within the column specified by the string passed in. It
   * takes in a list of lists containing strings (which is the parsed CSV) to be searched, a string
   * representing the column to search within, and a list of strings representing the CSV's header
   * so that it can identify which column to search in if given a column header.
   *
   * @param allRows a list of lists containing strings, representing a parsed CSV
   * @param col a string representing either a column index or column header
   * @param header a list of strings representing the CSV's header
   * @return a list of integers representing the rows containing the value
   * @throws IllegalArgumentException if given an out-of-range column index, a non-existent column
   *     header, or if caller attempts to search by column header when the CSV doesn't have a header
   */
  public List<List<String>> colSearch(List<List<String>> allRows, String col, List<String> header)
      throws IllegalArgumentException {
    List<List<String>> result = new ArrayList<>();
    int currRow = 0;
    int colNum = this.stringToCol(allRows.get(0).size(), col, header);

    while (currRow < allRows.size()) {
      if (allRows.get(currRow).get(colNum).equalsIgnoreCase(this.value)) {
        result.add(allRows.get(currRow));
      }
      currRow++;
    }
    return result;
  }

  /**
   * This is a helper method that converts the given col String into its corresponding column index.
   * It takes the given string, converts it to an integer if it is meant to be a column index, and
   * finds its index if it is meant to be a column header. It returns this integer or throws an
   * IllegalArgumentException if given an out-of-range column index, a non-existent column header,
   * or if caller attempts to search by column header when the CSV doesn't have a header.
   *
   * @param numCols the number of columns in the parsed CSV
   * @param col a string representing either a column index or column header
   * @param header a list of strings representing the CSV's header
   * @return an integer representing the index of the column to be searched
   * @throws IllegalArgumentException if given an out-of-range column index, a non-existent column
   *     header, or if caller attempts to search by column header when the CSV doesn't have a header
   */
  private int stringToCol(int numCols, String col, List<String> header)
      throws IllegalArgumentException {
    int colNum;
    if (this.isInt(col)) {
      colNum = Integer.parseInt(col);
      if (colNum < 0 || colNum >= numCols) {
        throw new IllegalArgumentException(
            "Attempted to search by an invalid column index: [" + colNum + "]. Available column indices are in the range: [0," + (numCols - 1) + "]");
      }
    } else {
      if (!header.isEmpty()) {
        colNum = header.indexOf(col);
        if (colNum == -1) {
          throw new IllegalArgumentException("Attempted to search by an invalid column header: [" + col + "]. Available columns are: " + header);
        }
      } else {
        throw new IllegalArgumentException(
            "Attempted to search by column header, but loaded file has no headers.");
      }
    }
    return colNum;
  }

  /**
   * This is a helper method that takes in a string representing either a column index or a column
   * header, and returns true if it is a column index and false if it is a column header.
   *
   * @param col the string representing either a column index or a column header
   * @return true if the string can be converted to an integer, false otherwise
   */
  private boolean isInt(String col) {
    try {
      Integer.parseInt(col);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * This method performs a regular search on a parsed CSV. It takes in a list of lists containing
   * strings (the parsed CSV), searches all values within the parsed CSV, and returns a list of
   * integers representing the rows in which the value was found.
   *
   * @param allRows a list of lists containing strings, representing the parsed CSV
   * @return a list of integers representing the rows containing the value
   */
  public List<List<String>> regSearch(List<List<String>> allRows) {
    List<List<String>> result = new ArrayList<>();
    int currRow = 0;
    while (currRow < allRows.size()) {
      for (String col : allRows.get(currRow)) {
        if (col.equalsIgnoreCase(this.value)) {
          result.add(allRows.get(currRow));
        }
      }
      currRow++;
    }
    return result;
  }
}
