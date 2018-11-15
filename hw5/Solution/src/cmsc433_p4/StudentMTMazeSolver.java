package cmsc433_p4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * This file needs to hold your solver to be tested. 
 * You can alter the class to extend any class that extends cmsc433_p4.MazeSolver.
 * It must have a constructor that takes in a cmsc433_p4.Maze.
 * It must have a solve() method that returns the datatype List<cmsc433_p4.Direction>
 *   which will either be a reference to a list of steps to take or will
 *   be null if the maze cannot be solved.
 */
public class StudentMTMazeSolver extends SkippingMazeSolver
{
    private final int numProcessors; /* Number of worker threads*/
    private final ExecutorService pool;

    public StudentMTMazeSolver(Maze maze)
    {
        super(maze);
        this.numProcessors = Runtime.getRuntime().availableProcessors();
        this.pool = Executors.newFixedThreadPool(numProcessors);
    }

    @Override
    public List<Direction> solve() {
        LinkedList<Helper> tasks = new LinkedList<>();//container for tasks
        List<Future<List<Direction>>> futures = new LinkedList<>(); //container for the future tasks' result
        List<Direction> res = null;//result

        try{
            Choice start = firstChoice(maze.getStart());
            while (!start.choices.isEmpty()){
                tasks.add(new Helper(follow(start.at, start.choices.peek()), start.choices.poll()));
            }
        } catch (SolutionFound solutionFound) {
            System.out.println("Solution found");
        }
        try{
            //Executor service would return a list of Futures holding their status and results when all complete.
            futures = pool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future<List<Direction>> ans: futures) {
            try{
                if (ans.isDone() && ans.get() != null){
                    res = ans.get();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        pool.shutdown();
        return res;
    }

    @SuppressWarnings("Duplicates")
    private class Helper implements Callable<List<Direction>> {
        private Choice choice;
        private Direction choiceDirection;

        Helper(Choice nextChoice, Direction choiceDirection) {
            this.choice = nextChoice;
            this.choiceDirection = choiceDirection;
        }

        @Override
        public List<Direction> call() {  //Exception handled inner function
            LinkedList<Choice> stack = new LinkedList<>();
            Choice currChoice;

            try{
                stack.push(this.choice);
                while (!stack.isEmpty()){
                    currChoice = stack.peek();
                    if (currChoice.isDeadend()){
                        stack.pop();
                        if (!stack.isEmpty()) stack.peek().choices.pop();
                        continue;
                    }
                    stack.push(follow(currChoice.at, currChoice.choices.peek()));
                }
                return null;
            }catch (SolutionFound e){
                Iterator<Choice> iter = stack.iterator();
                LinkedList<Direction> solutionPath = new LinkedList<Direction>();
                while (iter.hasNext())
                {
                    choice = iter.next();
                    solutionPath.push(choice.choices.peek());
                }
                solutionPath.push(choiceDirection);
                if (maze.display != null) maze.display.updateDisplay();
                return pathToFullPath(solutionPath);
            }
        }
    }

}
