//import csv.FactoryFailureException;
//import csv.Parser;
//import csv.rowcreators.MoveCreator;
//import csv.rowobjects.Move;
//import generator.RandomGenerator;
//import org.junit.jupiter.api.Test;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TestGenerator {
//
//    @Test
//    public void testFuzzGenerator() throws IOException, FactoryFailureException {
//        Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Full_1.csv"),new MoveCreator(), true);
//        RandomGenerator generator = new RandomGenerator(parser.parseLines());
//        for(int i = 0; i<1000; i++){
//            List<Move> res = generator.generate(8);
//            if(res.size() != 8){
//                System.out.println(res);
//            }
//            assertEquals(8, res.size());
//        }
//    }
//}
