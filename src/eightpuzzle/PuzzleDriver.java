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
        GameHandler handler = new GameHandler("034871652");
        if(handler.checkIfSolvable()){
            handler.solutionSearch();
            System.out.println(handler.outputSteps());
        }else{
            System.out.println("NOT SOLVABLE");
        }
    }
    
}
