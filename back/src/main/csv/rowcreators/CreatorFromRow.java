package csv.rowcreators;

import csv.FactoryFailureException;

import java.util.List;

/**
 * This is the CreatorFromRow interface. It declares a generic create method that takes in a row as
 * a list of strings, makes an object (specified by the implementing class) from the row, and throws
 * a FactoryFailureException if it was unable to create the object.
 *
 * @param <T> the object to be created from a row
 */
public interface CreatorFromRow<T> {

  /**
   * This method would take in a row as a list of strings, make an object (specified by the
   * implementing class) out of it, and throw a FactoryFailureException if it cannot create the
   * object.
   *
   * @param row a list of strings representing the row to be made into object
   * @return an object made using the row passed in
   * @throws FactoryFailureException if there was an error in creating the object from the row
   */
  T create(List<String> row) throws FactoryFailureException;
}
