//Christopher Kilian
//CS 420 - Project 1: 8-Puzzle

package eightpuzzle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This class handles the test cases for a submitted list of game strings. It maps the solution depths to the total sum cost of time and cost in nodes,
//and can return the averages of these values on a per-depth basis.
public class Tester {
    private Map<Integer, Long> totalTime; //maps depth to sum of time taken for all puzzles at that depth
    private Map<Integer, Integer> totalCost; //maps depth to sum of search costs in nodes for all puzzles at that depth
    private Map<Integer, Integer> numberOfInstances; //maps depth to number of games completed at that depth
    private String chosenHeuristic;
    private final String HEURISTIC_1 = "H1"; //corresponds to StateNodeH1 implementing heuristic #1 - number of misplaced tiles
    private final String HEURISTIC_2 = "H2"; //corresponds to StateNodeH2 implementing heuristic #2 - the sum of the distances of the tiles from their goal positions
    
    //constructor
    public Tester(String heuristic){
        totalTime = new HashMap<>();
        totalCost = new HashMap<>();
        numberOfInstances = new HashMap<>();
        if(heuristic.equals(HEURISTIC_2)){
            chosenHeuristic = HEURISTIC_2;
        }else{
            chosenHeuristic = HEURISTIC_1;
        }
    }
    
    
    //Primary method for running tests - It accepts a list of strings representing game boards and tests each one in turn, storing the results
    //in the member map values.
    public void runTests(List<String> gamesToTest){
        
        for(String game : gamesToTest){
            GameHandler handler = new GameHandler(game, chosenHeuristic);
            //System.out.println("Game number " + count);
            if(handler.checkIfSolvable()){
                long startTime = System.nanoTime();
                handler.getSolution();
                long elapsedTimeNanos = System.nanoTime() - startTime;
                Integer depth = handler.getDepth();
                
                if(totalTime.containsKey(depth)){ //increase stored value for total cost in time for this depth
                    long timeSum = totalTime.get(depth) + elapsedTimeNanos;
                    totalTime.put(depth, timeSum);
                }else{
                    totalTime.put(depth, elapsedTimeNanos);
                }
                
                Integer cost = handler.getTotalCost();
                if(totalCost.containsKey(depth)){ //increase stored value for total cost in nodes for this depth
                    Integer costSum = totalCost.get(depth) + cost;
                    totalCost.put(depth, costSum);
                }else{
                    totalCost.put(depth, cost);
                }
                
                if(numberOfInstances.containsKey(depth)){
                    numberOfInstances.put(depth, (numberOfInstances.get(depth) + 1)); //increment stored number of instances of this depth
                }else{
                    numberOfInstances.put(depth, 1);
                }
                //System.out.println("Gameboard initial value: " + handler.getInitialBoard());
                //System.out.println("DEPTH OF SOLUTION WAS " + handler.getDepth());
                //System.out.println(handler.outputSteps());
                //System.out.println("Depth was: " + handler.getDepth());
            }else{
                System.out.println("GAME " + game + " WAS NOT SOLVABLE");
            }
        }
    }

    
    //Uses the mapped times on a per-depth basis to get the average time it took to find a solution at that depth.
    //Returns a mapping of depths to their average times.
    public Map<Integer, Double> getAverageTimes(){
        Map<Integer, Double> averageTimesMap = new HashMap<>();
        
        for(Integer depth : totalTime.keySet()){
            long totalDepthTime = totalTime.get(depth);
            int totalAtDepth = numberOfInstances.get(depth);
            double averageTime = ((double)totalDepthTime) / totalAtDepth;
            averageTimesMap.put(depth, averageTime);
        }
        
        return averageTimesMap;
    }
    
    
    //Uses the mapped costs on a per-depth basis to get average cost of a solution at that depth.
    //Returns a mapping of depths to their average cost.
    public Map<Integer, Double> getAverageCosts(){
        Map<Integer, Double> averageCostMap = new HashMap<>();
        
        for(Integer depth : totalCost.keySet()){
            int totalDepthCost = totalCost.get(depth);
            int totalAtDepth = numberOfInstances.get(depth);
            double averageTime = ((double)totalDepthCost) / totalAtDepth;
            averageCostMap.put(depth, averageTime);
        }
        
        return averageCostMap;
    }
    
    //Getters for member variables
    public Map<Integer, Long> getTotalTime() {
        return totalTime;
    }

    public Map<Integer, Integer> getTotalCost() {
        return totalCost;
    }

    public Map<Integer, Integer> getNumberOfInstances() {
        return numberOfInstances;
    }
            
}
