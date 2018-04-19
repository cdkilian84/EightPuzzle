//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;


public abstract class StateNode implements Comparable<StateNode>{
    private StateNode parent; //parent reference
    private String boardState; //state of the board at this node
    private int costToNode; //cumulative step cost to this node
    //private int heuristicVal; //heuristic value for this node
    
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
    
    public abstract int getHeuristicVal();
    
    

    
    
    public String getNodeState(){
        return boardState;
    }
    
    public int getCostToNode(){
        return costToNode;
    }
    
    
    public StateNode getParent(){
        return parent;
    }

    @Override
    public abstract int compareTo(StateNode otherNode);
    
    //hash each node object based on its state string
    @Override
    public int hashCode(){
        return boardState.hashCode();
//        int primeval = 31;
//        int hash = boardState.hashCode();
//        if(parent != null){
//            hash += primeval * parent.hashCode();
//        }
//        return hash;
    }
    
    //two StateNodes are equal to each other if they represent the same state (same game board configuration)
    @Override
    public boolean equals(Object otherNode){
        boolean flag = false;
        
        if(this.boardState.equals(((StateNode)otherNode).getNodeState())){
            flag = true;
        }
        
        return flag;
    }
}
