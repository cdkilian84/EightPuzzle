//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


//This class handles the actual playing of the 8-puzzle game. It implements the A* search algorithm, and handles various other game related actions such as
//generating new nodes when appropriate and outputting various game values (such as a trace of the steps to the solution).
public class GameHandler {
    private StateNode root; //root node for the game - contains the starting configuration for an 8-puzzle
    private StateNode solutionNode;
    private int searchCost;
    private NodeFactory nodeBuilder; //for building instances of specific node types based on chosen heuristic
    private String chosenHeuristic;
    private final String HEURISTIC_1 = "H1"; //corresponds to StateNodeH1 implementing heuristic #1 - number of misplaced tiles
    private final String HEURISTIC_2 = "H2"; //corresponds to StateNodeH2 implementing heuristic #2 - the sum of the distances of the tiles from their goal positions
    private final String SOLUTION = "012345678";
    
    
    public GameHandler(String submittedGame, String heuristic){
        nodeBuilder = new NodeFactory();
        
        //If passed heuristic value is not heuristic_2, then default to heuristic_1
        if(heuristic.equals(HEURISTIC_2)){
            chosenHeuristic = HEURISTIC_2;
        }else{
            chosenHeuristic = HEURISTIC_1;
        }
        
        if(submittedGame.length() != 9){
            System.out.println("Invalid Game Board!!!");
        }else{
            root = nodeBuilder.getNode(chosenHeuristic, submittedGame, null); //new StateNodeH1(submittedGame, null);
        }
        searchCost = 0;
    }
    
    public boolean checkIfSolvable(){
        boolean solvable = false;
        
        if(root != null){
            StringBuilder temp = new StringBuilder(root.getNodeState()); //use to remove the "0" from the string before evaluating parity
            temp.deleteCharAt(temp.indexOf("0")); //delete 0 from the string - not used as part of solvability check
            String gameBoard = temp.toString();
            int inversionCount = 0;
            
            for(int i = 0; i < gameBoard.length(); i++){
                for(int j = i+1; j < gameBoard.length(); j++){                   
                    if(Character.getNumericValue(gameBoard.charAt(i)) > Character.getNumericValue(gameBoard.charAt(j))){ //if i-value is greater than j-value, increment inversion count
                        inversionCount++;
                    }
                }
            }
            
            if((inversionCount % 2) == 0){
                solvable = true; //gameboard is solvable if number of inversions is even
            }
        }
        
        return solvable;
    }
    
    
    //Public method for retrieving the solution state node - only runs solutionSearch if the solution node
    //has not already been found, otherwise just return the solution node.
    public StateNode getSolution(){
        
        if(solutionNode == null){
            solutionSearch();
        }
        
        return solutionNode;
    }
    
    
    //Main implementation of A* algorithm - search for an optimal solution to the given "root" gameboard
    //Maintains a frontier of unvisited nodes stored in a priority queue (nodes implement appropriate methods for proper queueing order),
    //as well as a hashset of explored nodes (to ensure no node is revisited). Graph search implementation of A* in other words.
    //When the goal node is reached, the solution node is saved as a member variable of the game handler for easy access later.
    private void solutionSearch(){
        StateNode node;
        Queue<StateNode> frontier = new PriorityQueue<>();
        Set<StateNode> explored = new HashSet<>();
        
        frontier.add(root);
        
        while(!frontier.isEmpty()){
            node = frontier.poll();

            if(goalTest(node)){
                solutionNode = node;
                System.out.println("Solution found with search cost of " + searchCost);
                break;
            }
            explored.add(node);
            for(StateNode childNode : nodeGenerator(node)){
                if(!explored.contains(childNode)){ //&& !frontier.contains(childNode)){
                    frontier.add(childNode);
                    searchCost++;
                }//else if(frontier.contains(childNode)){
                    //if the child node is already in the frontier but with a higher path cost, replace with lower cost child node
                 //   frontier = checkLowerCost(frontier, childNode);
                //}
            }
        }
    }
    
    
    //Check if the examined node is the goal node, meaning its state matches the solution string.
    private boolean goalTest(StateNode node){
        boolean goal = false;
        
        if(node.getNodeState().equals(SOLUTION)){
            goal = true;
        }
        
        return goal;
    }
    
    
    //Method to generate all possible new nodes (child nodes) for a given node state and return them as an array.
    //Potential moves are based on the location of the "0" in the string (the empty space) and have been hard-coded
    //based on this, as the possible 8-puzzle moves will always be the same.
    private List<StateNode> nodeGenerator(StateNode currentNode){
        List<StateNode> expandedNodes = new ArrayList<>(); //list of new nodes to be returned
        String currentBoard = currentNode.getNodeState();
        int emptySpace = currentBoard.indexOf("0"); //get index location of blank space in current node state
        
        switch(emptySpace){
            case 0:
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 0, 1), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 0, 3), currentNode));
                break;
            case 1:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 1, 0), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 1, 2), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 1, 4), currentNode));
                break;
            case 2:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 2, 1), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 2, 5), currentNode));
                break;
            case 3:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 3, 0), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 3, 4), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 3, 6), currentNode));
                break;
            case 4:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 4, 1), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 4, 3), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 4, 5), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 4, 7), currentNode));
                break;
            case 5:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 5, 2), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 5, 4), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 5, 8), currentNode));
                break;
            case 6:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 6, 3), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 6, 7), currentNode));
                break;
            case 7:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 7, 4), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 7, 6), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 7, 8), currentNode));
                break;
            case 8:  
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 8, 5), currentNode));
                expandedNodes.add(nodeBuilder.getNode(chosenHeuristic, moveTile(currentBoard, 8, 7), currentNode));
                break;
            default: 
                System.out.println("ERROR - IMPOSSIBLE EMPTY SPACE LOCATION DISCOVERED");
                break;
        }
                
        return expandedNodes;
    }
    
    
    //Swap the empty space tile with the tile being moved by converting the string to a char array,
    //swapping chars, then converting back to a string to be returned.
    private String moveTile(String board, int emptySpace, int tileToMove){
        char[] swap = board.toCharArray();

        char temp = swap[emptySpace];
        swap[emptySpace] = swap[tileToMove];
        swap[tileToMove] = temp;

        return new String(swap);
    }
    
    
    //Getter for the depth of the solution node.
    public int getDepth(){
        int depth = 0;
        
        if(solutionNode != null){
            StateNode currentNode = solutionNode;
            while(currentNode.getParent() != null){
                currentNode = currentNode.getParent();
                depth++;
            }
        }
        
        return depth;
    }
    
    
    //Getter for the initial (root) game state.
    public String getInitialBoard(){
        return root.getNodeState();
    }
    
    
    //Output a string representing the order of steps to be taken to reach the goal node from the
    //root node. Works backwards by tracing the path from goal to root, pushing these values to a stack,
    //and then reading out the stack to a StringBuilder.
    public String outputSteps(){
        StringBuilder output = new StringBuilder();
        if(solutionNode != null){
            Stack nodePath = new Stack();
            StateNode currentNode = solutionNode;
            while(currentNode != null){
                nodePath.push(currentNode);
                currentNode = currentNode.getParent();
            }
            
            int count = 0;
            while(!nodePath.isEmpty()){
                StateNode outputNode = (StateNode)nodePath.pop();
                output.append("Step " + count + ": " + outputNode.getNodeState() + "\n");
                count++;
            }
        }else{
            output.append("HAS NOT BEEN SOLVED YET");
        }
        
        //System.out.println("Root node heuristic val: " + root.getHeuristicVal());
        
        return output.toString();
    }
    
    
        //Method to check for and potentially replace a node from within the frontier priority queue - requires removing elements
    //from queue, storing them temporarily in a list until the desired node is found. Then checks can be made, and elements returned
    //to the queue, which will itself be returned.
//    private Queue<StateNode> checkLowerCost(Queue<StateNode> frontier, StateNode nodeToCheck){
//        Queue<StateNode> holdingQueue = new PriorityQueue<>();
//        
//        while(!frontier.isEmpty()){
//            StateNode frontierNode = frontier.poll();
//            if(frontierNode.equals(nodeToCheck) && 
//                    ((frontierNode.getCostToNode() + frontierNode.getHeuristicVal()) > (nodeToCheck.getCostToNode() + nodeToCheck.getHeuristicVal()))){
//                holdingQueue.add(nodeToCheck);
//            }else{
//                holdingQueue.add(frontierNode);
//            }
//        }
//        
//        return holdingQueue;
//    }
}
