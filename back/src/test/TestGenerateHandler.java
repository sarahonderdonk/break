//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import csv.FactoryFailureException;
import csv.Parser;
import csv.rowcreators.MoveCreator;
import csv.rowobjects.Move;
import generator.RandomGenerator;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import server.GenerateHandler;
//import spark.Spark;
//
///**
// * Test the actual handlers.
// */
//public class TestGenerateHandler {
//
//  @BeforeAll
//  public static void setup_before_everything() {
//    // Set the Spark port number
//    Spark.port(0);
//    Logger.getLogger("").setLevel(Level.WARNING); // empty name = root logger
//  }
//
//  @BeforeEach
//  public void setup() throws IOException, FactoryFailureException {
//    // In fact, restart the entire Spark server for every test!
//    Parser<Move> parser = new Parser<>(new FileReader("data/Sample_Full_1.csv"), new MoveCreator(), true);
//    RandomGenerator generator = new RandomGenerator(parser.parseLines());
//    Spark.get("/generate", new GenerateHandler(generator));
//    Spark.init();
//    Spark.awaitInitialization(); // don't continue until the server is listening
//  }
//
//  @AfterEach
//  public void teardown() {
//    // Gracefully stop Spark listening on both endpoints
//    Spark.unmap("/generate");
//    Spark.awaitStop(); // don't proceed until the server is stopped
//  }
//
//  /**
//   * Helper to start a connection to a specific API endpoint/params
//   * @param apiCall the call string, including endpoint
//   *                (NOTE: this would be better if it had more structure!)
//   * @return the connection for the given URL, just after connecting
//   * @throws IOException if the connection fails for some reason
//   */
//  static private HttpURLConnection tryRequest(String apiCall) throws IOException {
//    // Configure the connection (but don't actually send the request yet)
//    URL requestURL = new URL("http://localhost:"+Spark.port()+"/"+apiCall);
//    HttpURLConnection clientConnection = (HttpURLConnection) requestURL.openConnection();
//    clientConnection.connect();
//    return clientConnection;
//  }
//
//  /**
//   * Helper method that returns a string version of the response from the server
//   *
//   * @param clientConnection - represents the connection between the user and the server
//   * @returns a string version of the response from the server
//   */
//  static private String getResponse(HttpURLConnection clientConnection) throws IOException {
//    Moshi moshi = new Moshi.Builder().build();
//    Object response = moshi.adapter(
//            Types.newParameterizedType(Map.class, String.class, Object.class))
//        .fromJson(new Buffer().readFrom(clientConnection.getInputStream()));
//    return response.toString();
//  }
//
//  /**
//   * Tests that the appropriate error message is displayed if the user does not provide a length parameter
//   *
//   * @throws IOException
//   */
//  @Test
//  public void testNoLength() throws IOException {
//    HttpURLConnection clientConnection = tryRequest("generate");
//
//    // Get an OK response (the *connection* worked, the *API* provides an error response)
//    assertEquals(200, clientConnection.getResponseCode());
//    String response = getResponse(clientConnection);
//
//    // Check that the response contains critical elements
//    assertTrue(response.contains("Please provide a desired length for the sequence."));
//    assertTrue(response.contains("error_bad_request"));
//    clientConnection.disconnect();
//  }
//
//  /**
//   * Tests that the server is able to successfully return a sequence of moves
//   *
//   * @throws IOException
//   */
//  @Test
//  // Recall that the "throws IOException" doesn't signify anything but acknowledgement to the type checker
//  public void testSuccessfulResponse() throws IOException {
//    HttpURLConnection clientConnection = tryRequest("generate?length=4");
//
//    // Get an OK response (the *connection* worked, the *API* provides an error response)
//    assertEquals(200, clientConnection.getResponseCode());
//    String response = getResponse(clientConnection);
//
//    // Check that the response contains critical elements
//    assertTrue(response.contains("success"));
//
//    // Each move has a name field, so the number of times "name" appears should equal the number
//    // of moves returned
//    String findStr = "name";
//
//    assertEquals(countSubstr(response, findStr), 4);
//
//    clientConnection.disconnect();
//  }
//
//  /**
//   * Tests that the server does not crash when given a length less than 4 or greater than 12
//   *
//   * @throws IOException
//   */
//  @Test
//  // Recall that the "throws IOException" doesn't signify anything but acknowledgement to the type checker
//  public void testOutOfBounds() throws IOException {
//    HttpURLConnection clientConnection = tryRequest("generate?length=-1");
//
//    // Get an OK response (the *connection* worked, the *API* provides an error response)
//    assertEquals(200, clientConnection.getResponseCode());
//    String response = getResponse(clientConnection);
//
//    // Check that the response contains critical elements
//    assertTrue(response.contains("success"));
//
//    clientConnection = tryRequest("generate?length=100");
//    assertEquals(200, clientConnection.getResponseCode());
//    response = getResponse(clientConnection);
//
//    assertTrue(response.contains("success"));
//
//    clientConnection.disconnect();
//  }
//
//  /**
//   * Tests that the server can handle multiple requests in succession
//   *
//   * @throws IOException
//   */
//  @Test
//  // Recall that the "throws IOException" doesn't signify anything but acknowledgement to the type checker
//  public void testMultipleRequests() throws IOException {
//    HttpURLConnection clientConnection = tryRequest("generate?length=8");
//
//    // Get an OK response (the *connection* worked, the *API* provides an error response)
//    assertEquals(200, clientConnection.getResponseCode());
//    String response = getResponse(clientConnection);
//
//    // Check that the response contains critical elements
//    assertTrue(response.contains("success"));
//    assertEquals(countSubstr(response, "name"), 8);
//
//    // Second request, this time for a sequence of 5 moves
//    clientConnection = tryRequest("generate?length=5");
//    assertEquals(200, clientConnection.getResponseCode());
//    response = getResponse(clientConnection);
//
//    assertTrue(response.contains("success"));
//    assertEquals(countSubstr(response, "name"), 5);
//
//    clientConnection.disconnect();
//  }
//
//  /**
//   * Tests that the server does not crash given a float
//   *
//   * @throws IOException
//   */
//  @Test
//  // Recall that the "throws IOException" doesn't signify anything but acknowledgement to the type checker
//  public void testFloat() throws IOException {
//    HttpURLConnection clientConnection = tryRequest("generate?length=1.5");
//
//    // Get an OK response (the *connection* worked, the *API* provides an error response)
//    assertEquals(200, clientConnection.getResponseCode());
//    String response = getResponse(clientConnection);
//
//    // Check that the response contains critical elements
//    assertTrue(response.contains("error_bad_request"));
//    assertTrue(response.contains("Please provide an integer between 4 and 12, inclusive."));
//
//    clientConnection.disconnect();
//  }
//
//  /**
//   * Helper function that determines how many times a substring is found in a larger string
//   * @param haystack - the larger string
//   * @param needle - the substring
//   * @return the number of times needle is found in haystack
//   *
//   * Taken from https://stackoverflow.com/questions/767759/find-the-number-of-occurrences-of-a-substring-in-a-string
//   *
//   */
//  private int countSubstr(String haystack, String needle) {
//    int lastIndex = 0;
//    int count = 0;
//
//    while (lastIndex != -1) {
//      lastIndex = haystack.indexOf(needle, lastIndex);
//
//      if (lastIndex != -1) {
//        count++;
//        lastIndex += needle.length();
//      }
//    }
//
//    return count;
//  }
//}