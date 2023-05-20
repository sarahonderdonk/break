package generator;

import csv.rowobjects.Move;

import java.util.*;

public class RandomGenerator {
    private List<Move> moves;
    private Map<String, List<Move>> moveCategories;

    /**
     * Constructs a RandomGenerator object with the given list of moves.
     *
     * @param moves a List of Move objects that will be used to generate new sequences of moves
     */
    public RandomGenerator(List<Move> moves){
        this.moves = moves;
        this.makeMoveCategories(moves);
    }

    /**
     * Creates a Map of move categories from the given List of moves.
     *
     * @param moves a List of Move objects that will be categorized by type
     */
    private void makeMoveCategories(List<Move> moves){
        this.moveCategories = new HashMap<>();
        this.moveCategories.put("Toprock", new ArrayList<>());
        this.moveCategories.put("Footwork", new ArrayList<>());
        this.moveCategories.put("Freeze", new ArrayList<>());
        this.moveCategories.put("Power", new ArrayList<>());
        this.moveCategories.put("Go-Down", new ArrayList<>());
        for(Move m : moves){
            this.moveCategories.get(m.getType()).add(m);
        }
    }

    /**
     * Returns the Map of move categories, used for testing.
     *
     * @return a Map of String keys representing move types and List values of Move objects of that type
     */
    public Map<String, List<Move>> getMoveCategories(){
        return this.moveCategories;
    }

    /**
     * Generates a List of random Move objects of the given length.
     *
     * @param length the length of the desired sequence of moves
     * @return a List of random Move objects of the given length
     */
    public List<Move> generate(int length){

        //we budget our "sections" here - approximately 1/4 of our moves should be toprock, and the rest should be
        //footwork except for the last two moves and one get-down
        int toprockMoves = length/4;
        int footworkMoves = length-2;

        //we want to start in toprock, so we generate one move from the set of toprock moves
        int startIndex = this.moveCategories.get("Toprock").get((int)(Math.random()*this.moveCategories.get("Toprock").size())).getId();

        //initialize visited map. We want to be able to repeat moves a set amount of times, so we make a map instead
        //of an array
        Map<Integer, Integer> visited = new HashMap<>();

        //initialize the adjacency list based off of the links in the database. We initialize a seperate adjacency list
        //so we can remove elements from the adjacency list to prevent repeat moves.
        ArrayList<Integer>[] adjacencyList = new ArrayList[this.moves.size()];
        for(int i = 0; i< this.moves.size(); i++){
            adjacencyList[i] = new ArrayList<>();
        }
        for(Move m : this.moves){
            visited.put(m.getId(), 0);
            for(int link : m.getLinks()){
                adjacencyList[m.getId()].add(link);

            }
        }

        //initialize result array
        List<Move> res = new ArrayList<>();

        //A stack is used here as a standard tree generation data structure. This segment of the code can be easily
        //modified to be a standard graph generator if no other requirements are needed in terms of the order of
        //the moves.
        Stack<Move> stackingStacker = new Stack<Move>();

        //initial push to the stack
        stackingStacker.push(this.moves.get(startIndex));

        //while loop - keep generating moves until the graph is exhausted (which shouldn't happen)
        while (!stackingStacker.isEmpty()){

            //get the current move
            Move current = stackingStacker.pop();

            //if this move hasn't been "visited", then this move is valid, so add it to the result, and add one to its
            //visited count.
            if(visited.get(current.getId()) < 2) {
                res.add(current);
                visited.put(current.getId(), visited.get(current.getId())+1);
            }else{
                continue;
            }

            //if our resultant array is full now, then we can break out of the loop.
            if(res.size() >= length)
                break;

            //as long as there are remaining links from this move, we can re-run this code segment
            while(!adjacencyList[current.getId()].isEmpty()){

                //instantiate the variable to hold the index of the next move
                Integer moveIndex;

                //if we are in our toprock section
                if(res.size() < toprockMoves) {
                    moveIndex = generateOnType("Toprock", adjacencyList[current.getId()]);
                }
                //if we end the toprock section, we need to do a go-down and then start footwork
                else if (res.size() == toprockMoves){
                    res.add(this.moveCategories.get("Go-Down").get((int)(Math.random()*this.moveCategories.get("Go-Down").size())));
                    moveIndex = this.moveCategories.get("Footwork").get((int)(Math.random()*this.moveCategories.get("Footwork").size())).getId();
                }
                //if we are in the footwork section
                else if (res.size() < footworkMoves) {
                    moveIndex = generateOnType("Footwork", adjacencyList[current.getId()]);
                }
                //if we are in the end section, if there is a powermove we can transition to from the current move,
                //we will perform that powermove. Otherwise, we will perform a footwork move. We must end the set
                //on a freeze.
                else if (res.size() == length-2){
                    int flagIndex = generateOnType("Power", adjacencyList[current.getId()]);

                    //if there are no valid powermoves...
                    if(flagIndex == -1){
                        moveIndex = generateOnType("Footwork", adjacencyList[current.getId()]);
                    }else{
                        moveIndex = flagIndex;
                    }
                }else{
                    moveIndex = generateOnType("Freeze", adjacencyList[current.getId()]);
                }

                //if there are no valid moves, then we break
                if(moveIndex == -1){
                    break;
                }

                //if the move we just got has not been visited, we push it to the stack. Otherwise, we need to remove it
                //from the possible moves linked from this move, and then re-compute the current note to find a move
                //that isn't visited.
                if(visited.get(moveIndex) < 2){
                    stackingStacker.push(this.moves.get(moveIndex));
                    break;
                }else{
                    adjacencyList[current.getId()].remove(moveIndex);
                    stackingStacker.push(current);
                }
            }
        }
        return res;
    }

    /**
     * Generates a random Move object of the given type from the list of available moves.
     *
     * @param type the type of move to generate (e.g. "Toprock", "Footwork")
     * @param availableMoves a List of Integer move IDs that are available to generate from
     * @return the ID of a randomly generated Move object of the given type, or -1 if none are available
     */
    private int generateOnType(String type, ArrayList<Integer> availableMoves){
        //initialize arraylist to store our filtered moves
        ArrayList<Move> filteredMoves = new ArrayList<Move>();

        //if a category that doesn't exist is requested, then return error
        if(this.moveCategories.get(type) == null){
            return -1;
        }

        //to filter the available moves by type, we loop through the moves and see if they exist within their
        //category in movecategories.
        for(Integer i: availableMoves){
            if(this.moveCategories.get(type).contains(this.moves.get(i))){
                filteredMoves.add(this.moves.get(i));
            }
        }

        //if this ends up filtering out all of the moves, return -1 as an error.
        if(filteredMoves.isEmpty()){
            return -1;
        }
        return filteredMoves.get((int)(Math.random()*filteredMoves.size())).getId();
    }

    /**
     * Helper method used for testing generateOnType method.
     *
     * @param type the type of move to generate (e.g. "Toprock", "Footwork")
     * @param availableMoves a List of Integer move IDs that are available to generate from
     * @return the ID of a randomly generated Move object of the given type, or -1 if none are available
     */
    public int testGenerateOnType(String type, ArrayList<Integer> availableMoves){
        return generateOnType(type, availableMoves);
    }
}
