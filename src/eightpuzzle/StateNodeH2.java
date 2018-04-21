//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

//Concrete subclass of StateNode which implements Heuristic #2, based on the sum of the Manhattan distances of each tile to their
//appropriate locations.
public class StateNodeH2 extends StateNode {
    
    private int heuristicVal; //heuristic value for this node

    //constructor
    public StateNodeH2(String board, StateNode parentNode) {
        super(board, parentNode);
        heuristicVal = 0;
        manhattenDistanceHeuristic();
    }
    
    
    //heuristic #2 - the sum of the distances of the tiles from their goal positions (AKA Manhattan distance)
    private void manhattenDistanceHeuristic(){
        String gameBoard = super.getNodeState();
        int totalDistance = 0;
        
        //System.out.println("For board state: " + boardState);
        
        for(int i = 0; i < gameBoard.length(); i++){
            int characterVal = Character.getNumericValue(gameBoard.charAt(i));
            int distance = 0; //manhatten distance value for the i'th tile
            
            if(characterVal != 0){ //ignore "0" since it's not a tile, only representation of empty space
                //characterVal == tile value == goal location in string
                //i == current location in string
                distance += Math.abs((i % 3) - (characterVal % 3)); //calculate x-distance on game board = |(actual position % 3) - (goal position % 3)|
                distance += Math.abs((i / 3) - (characterVal / 3)); //calculate y-distance on game board = |(actual position / 3) - (goal position / 3)| using integer division
                totalDistance += distance;
            }
        }
        
        heuristicVal = totalDistance;
    }
    
    
    //Getter for the heuristic value.
    @Override
    public int getHeuristicVal() {
        return heuristicVal;
    }

    
    //Comparator based on the total cost value of a node, which is the sum of the path cost and heuristic value.
    @Override
    public int compareTo(StateNode otherNode) {
        int thisNodeVal = super.getCostToNode() + heuristicVal;
        int otherNodeVal = otherNode.getCostToNode() + otherNode.getHeuristicVal();
        if(thisNodeVal > otherNodeVal){
            return 1;
        }else if(thisNodeVal < otherNodeVal){
            return -1;
        }else{
            return 0;
        }
    }
    
}
