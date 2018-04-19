//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

/**
 *
 * @author Chris
 */
public class StateNodeH2 extends StateNode {
    
    private int heuristicVal; //heuristic value for this node

    public StateNodeH2(String board, StateNode parentNode) {
        super(board, parentNode);
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
                
                //System.out.println("For index value " + i + " and characterVal " + characterVal);
                //System.out.println("X-distance was: " + Math.abs((i % 3) - (characterVal % 3)));
                //System.out.println("Y-distance was: " + Math.abs((i / 3) - (characterVal / 3)));
                totalDistance += distance;
            }
        }
        
        heuristicVal = totalDistance;
    }
    

    @Override
    public int getHeuristicVal() {
        return heuristicVal;
    }

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
