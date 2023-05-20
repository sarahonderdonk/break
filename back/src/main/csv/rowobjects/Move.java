package csv.rowobjects;

import java.util.Arrays;

public class Move {

    private int id;
    private String name;
    private String type;
    private int difficulty;
    private int[] links;

    /**
     * Constructor for a Move based off of information stored in the database CSV
     * @param id id of the move
     * @param name name of the move
     * @param type type of move (eg. "Toprock", "Footwork", etc.)
     * @param difficulty difficulty of the move (eg. 1, 2, 3)
     * @param links String representing all the moves that this move can transition into
     */
    public Move(int id, String name, String type, int difficulty, String links) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.links = stringToIntArray(links);
    }

    /**
     * Helper method to parse the string read from the CSV into an array on integers
     * @param s string to be converted
     * @return integer array that represents the string
     */
    private int[] stringToIntArray(String s){
        String[] string = s.split(" ");

        int[] arr = new int[string.length];

        // parsing the String argument as a signed decimal
        // integer object and storing that integer into the
        // array
        for (int i = 0; i < string.length; i++) {
            arr[i] = Integer.valueOf(string[i]);
        }
        return arr;
    }

    /**
     * Tostring and getters
     */

    @Override
    public String toString() {
        return "Move{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", difficulty=" + difficulty +
                ", links=" + Arrays.toString(links) +
                '}';
    }

    public int getId() {
        return id;
    }

    public int[] getLinks(){
        return this.links;
    }

    public String getType() {
        return type;
    }
}
