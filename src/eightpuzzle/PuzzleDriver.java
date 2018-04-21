//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


//Driver class for the 8-Puzzle game project.
public class PuzzleDriver {
    private static List<String> games;
    private static List<Character> gameVals;
    private static final String HEURISTIC_1 = "H1"; //corresponds to StateNodeH1 implementing heuristic #1 - number of misplaced tiles
    private static final String HEURISTIC_2 = "H2"; //corresponds to StateNodeH2 implementing heuristic #2 - the sum of the distances of the tiles from their goal positions
    private static final String MENU_HEURISTIC = HEURISTIC_2; //the chosen heuristic for the menu based game - change this value to change the menu heuristic
    
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
        
        
        Console console = System.console();
        if (console == null) {
            System.out.println("No console: non-interactive mode!");
            System.exit(0);
        }
        
        boolean loopFlag = true;
        
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
                    for(int i = 0; i < 9; i++){ //check that the string contains only a single instance of each number 0-8
                        int checkVal = playerGame.indexOf(Integer.toString(i));
                        if(checkVal == -1){
                            validFlag = false;
                            break;
                        }
                    }
                    if(validFlag){
                        handler = new GameHandler(playerGame, MENU_HEURISTIC);
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
                    handler = new GameHandler(potentialGame, MENU_HEURISTIC);
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
        
        
        //Testing stuff happens here - commented out for submission
        /*
        List<String> allGames = generateAllTestCases(5000);
        Tester myTester = new Tester(HEURISTIC_2);
        
        myTester.runTests(allGames);
        Map<Integer, Double> averageTimes = myTester.getAverageTimes();
        Map<Integer, Double> averageCosts = myTester.getAverageCosts();
        Map<Integer, Integer> instances = myTester.getNumberOfInstances();
        StringBuilder output = new StringBuilder();
        int totalInstances = 0;
        NumberFormat formatter = new DecimalFormat("#0.00");
        
        
        for(Integer depth : averageTimes.keySet()){
            System.out.printf("Depth " + depth + ":\tTime: %.2f \n", averageTimes.get(depth));
            output.append(depth).append(",").append(instances.get(depth)).append(",").append(formatter.format(averageTimes.get(depth))).append("\n");
        }
        outputResults(output.toString(), "timeResults.csv", "Average Time");
        output = new StringBuilder();
        
        for(Integer depth : averageCosts.keySet()){
            System.out.printf("Depth " + depth + ":\tCost: %.2f \n", averageCosts.get(depth));
            output.append(depth).append(",").append(instances.get(depth)).append(",").append(formatter.format(averageCosts.get(depth))).append("\n");
        }
        outputResults(output.toString(), "costResults.csv", "Average Cost");
        
        for(Integer depth : instances.keySet()){
            System.out.println("Depth " + depth + ":\tInstances: " + instances.get(depth));
            totalInstances += instances.get(depth);
        }
        System.out.println("Total instances: " + totalInstances);
        */
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
    
    //generate all test cases before actually running the tests!
    //Note that the generated test cases are strings only, not Handler objects or Nodes
    public static List<String> generateAllTestCases(int numOfCases){
        List<String> allGames = new ArrayList<>();
        int count = 0;
        
        while(count < numOfCases){
            String potentialGame = randomGameGenerator();
            GameHandler handler = new GameHandler(potentialGame, HEURISTIC_1);
            if(handler.checkIfSolvable()){
                allGames.add(potentialGame);
                count++;
            }
        }
        
        return allGames;
    }
    
    
    //Method to output test results to a file. Used to generate CSV files for runtime/cost analysis.
    public static void outputResults(String resultsString, String fileName, String averageType){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new File(fileName));
            StringBuilder output = new StringBuilder();
            output.append("Depth,");
            output.append("Instances,");
            output.append(averageType);
            output.append("\n");
            
            output.append(resultsString);
        
            pw.write(output.toString());
            pw.close();
        }catch(Exception e){
            System.out.println("PROBLEM OUTPUTTING VALUES");
            System.out.println(e.getMessage());
        }
    }
    
    
}
