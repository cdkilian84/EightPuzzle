/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
thing
 */
public class GameHandler {
    private StateNode root; //root node for the game - contains the starting configuration for an 8-puzzle
    private StateNode solutionNode;
    private int searchCost;
    
    public GameHandler(){
        //with empty constructor, generate random game board
        searchCost = 0;
    }
    
    public GameHandler(String submittedGame){
        if(submittedGame.length() != 9){
            System.out.println("Invalid Game Board! Generating random one instead...");
        }else{
            root = new StateNode(submittedGame, null);
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
    
    //Main implementation of A* algorithm - search for an optimal solution to the given "root" gameboard
    public void solutionSearch(){
        StateNode node;
        Queue<StateNode> frontier = new PriorityQueue<>();
        Set<StateNode> explored = new HashSet<>();
        
        frontier.add(root);
        
        while(!frontier.isEmpty()){
            node = frontier.poll();
            //System.out.println("Examining node with state: " + node.getNodeState());
            if(goalTest(node)){
                solutionNode = node;
                System.out.println("Solution found with search cost of " + searchCost);
                break;
            }
            explored.add(node);
            for(StateNode childNode : nodeGenerator(node)){
                if(!explored.contains(childNode) && !frontier.contains(childNode)){
                    frontier.add(childNode);
                    searchCost++;
                }else if(frontier.contains(childNode)){
                    //if the child node is already in the frontier but with a higher path cost, replace with lower cost child node
                    frontier = checkLowerCost(frontier, childNode);
                }
            }
        }
        
    }
    
    
    //check if the examined node is the goal node, meaning its state matches the string "012345678"
    private boolean goalTest(StateNode node){
        boolean goal = false;
        
        if(node.getNodeState().equals("012345678")){
            goal = true;
        }
        
        return goal;
    }
    
    
    //generate all possible new nodes (child nodes) for a given node state and return them as an array
    private List<StateNode> nodeGenerator(StateNode currentNode){
        List<StateNode> expandedNodes = new ArrayList<>(); //list of new nodes to be returned
        String currentBoard = currentNode.getNodeState();
        int emptySpace = currentBoard.indexOf("0"); //get index location of blank space in current node state
        
        switch(emptySpace){
            case 0:
                expandedNodes.add(new StateNode(moveTile(currentBoard, 0, 1), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 0, 3), currentNode));
                break;
            case 1:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 1, 0), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 1, 2), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 1, 4), currentNode));
                break;
            case 2:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 2, 1), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 2, 5), currentNode));
                break;
            case 3:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 3, 0), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 3, 4), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 3, 6), currentNode));
                break;
            case 4:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 4, 1), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 4, 3), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 4, 5), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 4, 7), currentNode));
                break;
            case 5:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 5, 2), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 5, 4), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 5, 8), currentNode));
                break;
            case 6:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 6, 3), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 6, 7), currentNode));
                break;
            case 7:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 7, 4), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 7, 6), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 7, 8), currentNode));
                break;
            case 8:  
                expandedNodes.add(new StateNode(moveTile(currentBoard, 8, 5), currentNode));
                expandedNodes.add(new StateNode(moveTile(currentBoard, 8, 7), currentNode));
                break;
            default: 
                System.out.println("MASSIVE ERROR");
                break;
        }
        
        return expandedNodes;
    }
    
    
    //Method to check for and potentially replace a node from within the frontier priority queue - requires removing elements
    //from queue, storing them temporarily in a list until the desired node is found. Then checks can be made, and elements returned
    //to the queue, which will itself be returned.
    private Queue<StateNode> checkLowerCost(Queue<StateNode> frontier, StateNode nodeToCheck){
        Queue<StateNode> holdingQueue = new PriorityQueue<>();
        
        while(!frontier.isEmpty()){
            StateNode frontierNode = frontier.poll();
            if(frontierNode.equals(nodeToCheck) && 
                    ((frontierNode.getCostToNode() + frontierNode.getHeuristicVal()) > (nodeToCheck.getCostToNode() + nodeToCheck.getHeuristicVal()))){
                holdingQueue.add(nodeToCheck);
            }else{
                holdingQueue.add(frontierNode);
            }
        }
        
        return holdingQueue;
    }
    
   
    
    //swap the empty space tile with the tile being moved by converting the string to a char array,
    //swapping chars, then converting back to a string to be returned.
    private String moveTile(String board, int emptySpace, int tileToMove){
        char[] swap = board.toCharArray();

        char temp = swap[emptySpace];
        swap[emptySpace] = swap[tileToMove];
        swap[tileToMove] = temp;

        return new String(swap);
    }
    
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
        return output.toString();
    }
    
}
