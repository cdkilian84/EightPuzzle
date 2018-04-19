//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

//Concrete subclass of StateNode which implements Heuristic #1, based on the number of misplaced tiles.
public class StateNodeH1 extends StateNode {
    
    private int heuristicVal; //heuristic value for this node
    
    //constructor
    public StateNodeH1(String board, StateNode parentNode) {
        super(board, parentNode);
        heuristicVal = 0;
        misplacedHeuristic();
    }
    
    
    //Heuristic #1 - number of misplaced tiles.
    //Implemented by checking the numeric value of each character in the string, and matching it to its index value.
    //If the values match, the tile isn't misplaced, and if they don't match, the count is incremented.
    private void misplacedHeuristic(){
        String gameBoard = super.getNodeState();
                
        for(int i = 0; i < gameBoard.length(); i++){
            int characterVal = Character.getNumericValue(gameBoard.charAt(i));
            //Note that the "0" tile is being ignored, as it only represents the empty space and not an actual game tile
            if((characterVal != 0) && (characterVal != i)){ 
                heuristicVal++;
            }
        }
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
