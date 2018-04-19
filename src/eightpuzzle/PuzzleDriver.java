//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class PuzzleDriver {
    private static List<String> games;
    private static final String HEURISTIC_1 = "H1"; //corresponds to StateNodeH1 implementing heuristic #1 - number of misplaced tiles
    private static final String HEURISTIC_2 = "H2"; //corresponds to StateNodeH2 implementing heuristic #2 - the sum of the distances of the tiles from their goal positions
    
    public static void main(String[] args) {
        
        readFile("gameData.txt");
        
        for(String game : games){
            GameHandler handler = new GameHandler(game, HEURISTIC_2);
            if(handler.checkIfSolvable()){
                handler.getSolution();
                if(handler.getDepth() != 20){
                    System.out.println("ERROR WITH GAME BOARD: " + game);
                    System.out.println("DEPTH OF SOLUTION WAS " + handler.getDepth());
                }
                //System.out.println(handler.outputSteps());
                //System.out.println("Depth was: " + handler.getDepth());
            }else{
                System.out.println("GAME " + game + " WAS NOT SOLVABLE");
            }
        }
        
//        GameHandler handler = new GameHandler("042187365");
//        if(handler.checkIfSolvable()){
//            handler.getSolution();
//            System.out.println(handler.outputSteps());
//            System.out.println("Depth was: " + handler.getDepth());
//        }else{
//            System.out.println("NOT SOLVABLE");
//        }
    }
    
    public static void readFile(String fileName) {
        games = new ArrayList<>();
        String gameBoard = "";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                gameBoard = line;
                games.add(gameBoard);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
