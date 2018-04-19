//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

//A simple factory object which will accept a given type, along with the game board string and parent node to return an appropriate
//concrete object node (dependent on which heuristic is being used).
public class NodeFactory {
    
    public StateNode getNode(String type, String gameBoard, StateNode parent){
        StateNode node = null;
        
        if(type.equals("H1")){
            node = new StateNodeH1(gameBoard, parent);
        }else if(type.equals("H2")){
            node = new StateNodeH2(gameBoard, parent);
        }
        
        return node;
    }
}
