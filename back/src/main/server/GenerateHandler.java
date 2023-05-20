package server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;
import generator.RandomGenerator;
import csv.rowobjects.Move;

/**
 * Handler class for requests to the /generate endpoint
 *
 * Returns a success response if the sequence generation was successful, or an appropriate error message
 * depending on what went wrong
 */
public class GenerateHandler implements Route {
  private RandomGenerator generator;

  public GenerateHandler(RandomGenerator generator) {
    this.generator = generator;
  }


  /**
   * Handles requests to the server to generate sequences of breaking moves
   *
   * @param request  - HTTP request from the user
   * @param response - HTTP response from the server
   * @return GenerateResponse - JSON response that reports whether the generation was successful
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // Will keep track of mappings between fields and content that will be returned
    Map<String, Object> jsonMap = new LinkedHashMap<>();

    String lengthStr = request.queryParams("length");

    if (lengthStr == null) {
      jsonMap.put("result", "error_bad_request");
      jsonMap.put("message", "Please provide a desired length for the sequence.");

      return new GenerateResponse(jsonMap).serialize();
    }

    List<Move> moveSequence;

    try {
      int length = Integer.parseInt(lengthStr);

      // Return the sequence
      moveSequence = this.generator.generate(length);

      jsonMap.put("result", "success");
      jsonMap.put("data", moveSequence);
    } catch (NumberFormatException e) {
      jsonMap.put("result", "error_bad_request");
      jsonMap.put("message", "Please provide an integer between 4 and 12, inclusive.");
    } catch (Exception e) {
      jsonMap.put("result", "error");
      jsonMap.put("message", "An unknown error occurred");
    }

      return new GenerateResponse(jsonMap).serialize();
  }

  /**
   * A JSON response that returns a success message if the sequence generation was successful, or an error message
   * if something went wrong
   *
   * @param jsonMap - the map that will be serialized into a JSON
   * @return this response, serialized as Json
   */
  public record GenerateResponse(Map<String, Object> jsonMap) {

    public String serialize() {
      try {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(
            Types.newParameterizedType(Map.class, String.class, Object.class));
        return jsonAdapter.indent("  ").toJson(jsonMap);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
    }
  }
}