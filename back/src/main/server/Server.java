package server;

import static spark.Spark.after;

import csv.rowcreators.MoveCreator;
import generator.RandomGenerator;
import java.io.FileReader;
import java.io.IOException;
import spark.Spark;

/** the Main class of our project. this is where execution begins. */
public final class Server {

  /**
   * the initial method called when execution begins.
   * @param args an array of command line arguments
   */
  public static void main(String[] args) throws IOException {

    Spark.port(3230);
    /*
       Setting CORS headers to allow cross-origin requests from the client; this is necessary for the client to
       be able to make requests to the edu.brown.cs.student.main.server.

       By setting the Access-Control-Allow-Origin header to "*", we allow requests from any origin.
       This is not a good idea in real-world applications, since it opens up your edu.brown.cs.student.main.server to cross-origin requests
       from any website. Instead, you should set this header to the origin of your client, or a list of origins
       that you trust.

       By setting the Access-Control-Allow-Methods header to "*", we allow requests with any HTTP method.
       Again, it's generally better to be more specific here and only allow the methods you need, but for
       this demo we'll allow all methods.

       We recommend you learn more about CORS with these resources:
           - https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
           - https://portswigger.net/web-security/cors
    */

    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    // Setting up the handler for the map endpoint
    try {
      csv.Parser<csv.rowobjects.Move> parser = new csv.Parser<>(
          new FileReader("back/data/Sample_Full_1.csv"), new MoveCreator(), true);
      RandomGenerator generator = new RandomGenerator(parser.parseLines());
      Spark.get("generate", new GenerateHandler(generator));
      Spark.init();
      Spark.awaitInitialization();
      System.out.println("Server started at http://localhost:3230");
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
