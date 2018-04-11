/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class PuzzleDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameHandler handler = new GameHandler("812043765");
        PriorityQueue<StateNode> explored = new PriorityQueue<>();
        
        StateNode nodeA = new StateNode("182043765", null);
        StateNode nodeB = new StateNode("182043765", nodeA);
        
        explored.add(nodeA);
        if(explored.contains(nodeB)){
            System.out.println("HASH WORKS");
        }else{
            System.out.println("Noooo");
        }
    }
    
}
