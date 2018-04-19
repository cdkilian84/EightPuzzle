//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Pattern;


//Driver class for the 8-Puzzle game project.
public class PuzzleDriver {
    private static List<String> games;
    private static List<Character> gameVals;
    private static final String HEURISTIC_1 = "H1"; //corresponds to StateNodeH1 implementing heuristic #1 - number of misplaced tiles
    private static final String HEURISTIC_2 = "H2"; //corresponds to StateNodeH2 implementing heuristic #2 - the sum of the distances of the tiles from their goal positions

    
    public static void main(String[] args) {
        //Setup gameVals so it can be used to easily generate random game boards
        gameVals = new ArrayList<>();
        gameVals.add('0');
        gameVals.add('1');
        gameVals.add('2');
        gameVals.add('3');
        gameVals.add('4');
        gameVals.add('5');
        gameVals.add('6');
        gameVals.add('7');
        gameVals.add('8');
        
        //readFile("gameData20.txt");
        
        
        Console console = System.console();
        if (console == null) {
            System.out.println("No console: non-interactive mode!");
            System.exit(0);
        }
        
        boolean loopFlag = true;
        Pattern checker = Pattern.compile("\\d{9}");
        
        while(loopFlag){
            GameHandler handler;
            System.out.println("Choose to see a random game played, or enter your own game:");
            System.out.println("1) Random Game");
            System.out.println("2) Enter a custom game");
            String choice = "0";
            while((!choice.equals("1")) && (!choice.equals("2"))){
                System.out.println("Enter either 1 or 2: ");
                choice = console.readLine();
            }
            if(choice.equals("2")){
                String playerGame;
                while(true){
                    System.out.println("Enter a game in the following format: 0 1 2 3 4 5 6 7 8");
                    System.out.println("Ensure you randomize the numbers though!");
                    playerGame = console.readLine();
                    playerGame = playerGame.replaceAll("\\s+",""); //cut out whitespace
                    boolean validFlag = true;
                    for(int i = 0; i < 9; i++){
                        int checkVal = playerGame.indexOf(Integer.toString(i));
                        if(checkVal == -1){
                            validFlag = false;
                            break;
                        }
                    }
                    if(validFlag){ //checker.matcher(playerGame).matches()
                        handler = new GameHandler(playerGame, HEURISTIC_2);
                        if(handler.checkIfSolvable()){
                            break;
                        }else{
                            System.out.println("That game is not solvable. Enter another game.");
                        }
                    }else{
                        System.out.println("Incorrect input. Only input exactly 9 numbers 0 through 8. Ensure all numbers 0 through 8 are present once only.\n");
                    }
                }
                System.out.println("Here are the steps to complete your game: ");
                handler.getSolution();
                System.out.println(handler.outputSteps());
                loopFlag = false;
            }else{
                while(true){
                    String potentialGame = randomGameGenerator();
                    handler = new GameHandler(potentialGame, HEURISTIC_2);
                    if(handler.checkIfSolvable()){
                        break;
                    }
                }
                handler.getSolution();
                System.out.println("Here are the steps to complete your game: ");
                System.out.println(handler.outputSteps());
                loopFlag = false;
            }
        }
        
        
//        games = generateAllTestCases();
//        int count = 1;
//        
//        for(String game : games){
//            GameHandler handler = new GameHandler(game, HEURISTIC_2);
//            System.out.println("Game number " + count);
//            if(handler.checkIfSolvable()){
//                handler.getSolution();
//                System.out.println("Gameboard initial value: " + handler.getInitialBoard());
//                System.out.println("DEPTH OF SOLUTION WAS " + handler.getDepth());
//                //System.out.println(handler.outputSteps());
//                //System.out.println("Depth was: " + handler.getDepth());
//            }else{
//                System.out.println("GAME " + game + " WAS NOT SOLVABLE");
//            }
//            count++;
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
    
    //randomizes the list of valid characters for a game board and then outputs them to a string
    public static String randomGameGenerator(){
        StringBuilder newGame = new StringBuilder();
        Collections.shuffle(gameVals);
        for(Character nextChar : gameVals){
            newGame.append(nextChar);
        }
        
        return newGame.toString();
    }
    
    //generate all 400+ test cases before actually running the tests!
    public static List<String> generateAllTestCases(){
        List<String> allGames = new ArrayList<>();
        int count = 0;
        
        while(count < 400){
            String potentialGame = randomGameGenerator();
            GameHandler handler = new GameHandler(potentialGame, HEURISTIC_1);
            if(handler.checkIfSolvable()){
                allGames.add(potentialGame);
                count++;
            }
        }
        
        return allGames;
    }
}
