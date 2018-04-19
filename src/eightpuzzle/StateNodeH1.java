//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

/**
 *
 * @author Chris
 */
public class StateNodeH1 extends StateNode {
    
    private int heuristicVal; //heuristic value for this node
    
    public StateNodeH1(String board, StateNode parentNode) {
        super(board, parentNode);
        misplacedHeuristic();
    }
    
    //heuristic #1 - number of misplaced tiles
    private void misplacedHeuristic(){
        String gameBoard = super.getNodeState();
                
        for(int i = 0; i < gameBoard.length(); i++){
            int characterVal = Character.getNumericValue(gameBoard.charAt(i));
            //tiles are misplaced when their numeric values don't match their string indices
            //Note that the "0" tile is being ignored, as it only represents the empty space and not an actual game tile
            if((characterVal != 0) && (characterVal != i)){ 
                heuristicVal++;
            }
        }
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
