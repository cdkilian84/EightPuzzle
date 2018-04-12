/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle;


public class StateNode implements Comparable<StateNode>{
    private StateNode parent; //parent reference
    private String boardState; //state of the board at this node
    private int costToNode; //cumulative step cost to this node
    private int heuristicVal; //heuristic value for this node
    
    //constructor
    public StateNode(String board, StateNode parentNode){
        heuristicVal = 0;
        boardState = board;
        misplacedHeuristic();
        if(parentNode != null){
            //step cost is constant - 1 point per node away from root, meaning each child node is 
            //1 point more expensive than its parent
            parent = parentNode;
            costToNode = parent.getCostToNode() + 1; 
        }else{
            costToNode = 0; //only node without a parent is root node, and root node cost is 0
        }
    }
    
    //heuristic #1 - number of misplaced tiles
    private void misplacedHeuristic(){
        for(int i = 0; i < boardState.length(); i++){
            if(Character.getNumericValue(boardState.charAt(i)) != i){ //tiles are misplaced when their numeric values don't match their string indices
                heuristicVal++;
            }
        }
    }
    
    
    public String getNodeState(){
        return boardState;
    }
    
    public int getCostToNode(){
        return costToNode;
    }
    
    public int getHeuristicVal(){
        return heuristicVal;
    }
    
    public StateNode getParent(){
        return parent;
    }

    @Override
    public int compareTo(StateNode otherNode) {
        int thisNodeVal = costToNode + heuristicVal;
        int otherNodeVal = otherNode.getCostToNode() + otherNode.getHeuristicVal();
        if(thisNodeVal > otherNodeVal){
            return 1;
        }else if(thisNodeVal < otherNodeVal){
            return -1;
        }else{
            return 0;
        }
    }
    
    //hash each node object based on its state string
    @Override
    public int hashCode() {
        return boardState.hashCode();
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
