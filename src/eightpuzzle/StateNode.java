//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;


//Abstract class representing a state node - each node contains the current state of the game board (represented as a string), a reference
//to the parent node (null when the node is the root), and the path cost to this node. Concrete subclasses must implement the heuristic method 
//themselves to be returned, as well as the "compareTo" method (which relies on knowledge of the heuristic value).
public abstract class StateNode implements Comparable<StateNode>{
    private StateNode parent; //parent reference
    private String boardState; //state of the board at this node
    private int costToNode; //cumulative step cost to this node

    
    //constructor
    public StateNode(String board, StateNode parentNode){
        boardState = board;
        if(parentNode != null){
            //step cost is constant - 1 point per node away from root, meaning each child node is 
            //1 point more expensive than its parent
            parent = parentNode;
            costToNode = parent.getCostToNode() + 1; 
        }else{
            costToNode = 0; //only node without a parent is root node, and root node cost is 0
        }
    }
    
    //abstract methods to be implemented by subclasses
    public abstract int getHeuristicVal();
    
    
    @Override
    public abstract int compareTo(StateNode otherNode);
    
    
    //Getters for the various private member variables.
    public String getNodeState(){
        return boardState;
    }
    
    
    public int getCostToNode(){
        return costToNode;
    }
    
    
    public StateNode getParent(){
        return parent;
    }
    
    
    //Hash each node object based on its state string.
    @Override
    public int hashCode(){
        return boardState.hashCode();
    }
    
    
    //Two StateNodes are equal to each other if they represent the same state (same game board configuration).
    @Override
    public boolean equals(Object otherNode){
        boolean flag = false;
        
        if(this.boardState.equals(((StateNode)otherNode).getNodeState())){
            flag = true;
        }
        
        return flag;
    }
}
