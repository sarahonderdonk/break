package generator;

import csv.FactoryFailureException;
import csv.Parser;
import csv.rowcreators.MoveCreator;
import csv.rowobjects.Move;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Main class that generates a list of randomized Move objects from a CSV file.
 */
public class Main {

    /**
     * Main method that reads a CSV file, parses its content into Move objects,
     * and generates a list of randomized Move objects.
     *
     * @param args command line arguments (not used)
     * @throws IOException if there is an error reading the CSV file
     * @throws FactoryFailureException if there is an error creating the MoveCreator
     */
    public static void main (String[] args)throws IOException, FactoryFailureException {
        Parser<Move> parser = new Parser<>(new FileReader("back/data/Sample_Full_1.csv"), new MoveCreator(), true);
        RandomGenerator generator = new RandomGenerator(parser.parseLines());
        for(int i = 0; i<10; i++){
            List<Move> res = generator.generate(8);
        }
    }
}
