package csv;

import csv.rowcreators.CreatorFromRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Parser class. It takes in an instance of a Reader class used to read the CSV, an
 * instance of CreatorFromRow, used to convert rows of the CSV to a specified object, as well as a
 * boolean that indicates whether the CSV should be parsed with or without headers. The Parser class
 * will parse a given CSV using the specified Reader, and return a list of objects specified by the
 * CreatorFromRow class that gets passed in. It can also store the header and return it.
 *
 * @param <T> the object that rows will be converted to
 */
public class Parser<T> {

  /**
   * Instance variables for Parser keep track of the BufferedReader wrapped around the given Reader,
   * the CreatorFromRow class needed to convert rows to objects, the boolean indicating if a CSV has
   * headers, and a list of Strings representing the CSV header.
   */
  private final BufferedReader buffReader;
  private final CreatorFromRow<T> rowCreator;
  private final boolean hasHeader;
  private final List<String> fileHeader;
  private final List<T> fileData;

  /**
   * The constructor for the Parser class. It wraps the passed in Reader in a BufferedReader, and
   * accepts other arguments like the CreatorFromRow and the header boolean, storing them as
   * instance variables. It initializes the header to be an empty list.
   *
   * @param reader the Reader that will be wrapped in BufferedReader, used to read from CSV
   * @param creator the CreatorFromRow class that creates specified objects out of rows
   * @param wantHeader boolean indicating whether the CSV has a header
   */
  public Parser(Reader reader, CreatorFromRow<T> creator, boolean wantHeader) {
    this.buffReader = new BufferedReader(reader);
    this.rowCreator = creator;
    this.hasHeader = wantHeader;
    this.fileHeader = new ArrayList<>();
    this.fileData = new ArrayList<>();
  }

  /**
   * This method parses a CSV into a list of objects specified by the CreatorFromRow class, and
   * returns this list.
   *
   * @return a list of objects specified by CreatorFromRow class
   * @throws IOException thrown when BufferedReader fails to read from CSV
   * @throws FactoryFailureException thrown when CreatorFromRow fails to create object out of row
   */
  public List<T> parseLines() throws IOException, FactoryFailureException {
    if (this.hasHeader) {
      String firstLine = this.buffReader.readLine();
      if (firstLine == null) {
        return new ArrayList<>(this.fileData);
      } else {
        this.fileHeader.addAll(List.of(firstLine.split(",")));
      }
    }
    String line = this.buffReader.readLine();
    while (line != null) {
      List<String> row = List.of(line.split(","));
      this.fileData.add(this.rowCreator.create(row));
      line = this.buffReader.readLine();
    }
    return new ArrayList<>(this.fileData);
  }

  /**
   * This method gives the header that was stored in the parseLines method. If the parsed CSV had no
   * header, it returns an empty list.
   *
   * @return a list of strings representing the header of the CSV
   */
  public List<String> getFileHeader() {
    return new ArrayList<>(this.fileHeader);
  }
}
