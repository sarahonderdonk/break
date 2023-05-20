package generator;

import csv.FactoryFailureException;
import csv.Parser;
import csv.rowcreators.MoveCreator;
import csv.rowobjects.Move;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGenerator {

    @Test
    public void testFuzzGenerator() throws IOException, FactoryFailureException {
        Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Full_1.csv"),new MoveCreator(), true);
        RandomGenerator generator = new RandomGenerator(parser.parseLines());
        for(int i = 0; i<1000; i++){
            List<Move> res = generator.generate(8);
            if(res.size() != 8){
                System.out.println(res);
            }
            assertEquals(8, res.size());
        }
    }

    @Test
    public void testMakeCategories() throws IOException, FactoryFailureException {
        Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Data.csv"),new MoveCreator(), true);
        RandomGenerator generator = new RandomGenerator(parser.parseLines());
        Map<String, List<Move>> res = generator.getMoveCategories();
        assertEquals(res.get("Toprock").size(), 2);
        assertEquals(res.get("Footwork").size(), 3);
        assertEquals(res.get("Power").size(), 1);
        assertEquals(res.get("Freeze").size(), 1);
        assertEquals(res.get("Go-Down").size(), 1);
    }

    @Test
    public void testGenerateOnType() throws IOException, FactoryFailureException{
        Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Data.csv"),new MoveCreator(), true);
        RandomGenerator generator = new RandomGenerator(parser.parseLines());
        ArrayList<Integer> moveTypes = new ArrayList<Integer>(Arrays.asList(0, 2, 5, 6, 7));
        assertEquals(generator.testGenerateOnType("Toprock", moveTypes), 0);
        assertEquals(generator.testGenerateOnType("Footwork", moveTypes), 2);
        assertEquals(generator.testGenerateOnType("Freeze", moveTypes), 5);
        assertEquals(generator.testGenerateOnType("Power", moveTypes), 6);
        assertEquals(generator.testGenerateOnType("Go-Down", moveTypes), 7);
    }
    @Test
    public void testGenerateOnTypeError() throws IOException, FactoryFailureException {
        Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Data.csv"),new MoveCreator(), true);
        RandomGenerator generator = new RandomGenerator(parser.parseLines());
        ArrayList<Integer> moveTypes = new ArrayList<Integer>(Arrays.asList(0, 2, 5, 6));
        assertEquals(generator.testGenerateOnType("Go-Down", moveTypes), -1);
        assertEquals(generator.testGenerateOnType("Nonsense", moveTypes), -1);
    }
}
