package libsudoku.solver;

import libsudoku.validator.Validator;
import libsudoku.validator.BasicValidator;
import libsudoku.board.Board;
import libsudoku.fileworker.*;
import java.util.Random;


public class RandomBruteForceSolver implements Solver{
    
    
    
    public RandomBruteForceSolver(){ 
        randomizeValues();        
    }
    
    public RandomBruteForceSolver(Validator validator){
        this.validator = validator;
    }
    
    @Override
    public Board solve(Board unsolvedBoard){
        randomizeValues();
        
        // Make sure that user supplied board is valid
        if(!validator.boardIsValid(unsolvedBoard)){
            return null;
        }
        
        // Create a new board that will be the 'working space' for the 
        // recursive solve method. This board will hold the solution
        // if a solution can be found.
        Board potentialSolution = new Board(unsolvedBoard);
        
        // Solve the board and return if valid
        // !!! extra boardIsValid() check may be redundant and unnecessary... investigate !!!
        if(recursivelySolve(unsolvedBoard, potentialSolution, 0, 0) && validator.boardIsValid(potentialSolution)){
            return potentialSolution;
        }
        else{
            return null;
        }
        
        
    }
    
    
    /* -------------- private ------------------------- */
    
    
    private Validator validator = new BasicValidator();
    private Random random = new Random();
    private int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    private void randomizeValues(){
        for(int i = 0; i < 40; i++){
            int index1 = random.nextInt(9);
            int index2 = random.nextInt(9);
            
            int temp = values[index1];
            values[index1] = values[index2];
            values[index2] = temp;
        }
    }
    
    
    private boolean recursivelySolve(Board unsolvedBoard, Board potentialSolution, int column, int row){
        // If we have already reached the 9th row (off the board), then 
        // a solution has been reached already -> return true
        if(row >= 9)
            return true;
        
        // Determine what is the next cell after the current cell
        int nextColumn = column + 1;
        int nextRow = row;
        if(nextColumn >= 9){
            nextRow++;
            nextColumn = 0;
        }
        
        // If current cell already has a value in the unsolved board, we do not
        // need to solve it. -> recursively call this function on the next cell
        if(unsolvedBoard.getValue(column, row) > 0 && unsolvedBoard.getValue(column, row) < 10)
            return recursivelySolve(unsolvedBoard, potentialSolution, nextColumn, nextRow);
        
        // Try values for the current cell and then recursively call this method
        // for the next cell. If recursive call is successful, we have found a valid
        // value. If it is not successful, we should try another value.
        // Try values 1-9. 
        for(int i = 0; i <9; i++){
            potentialSolution.setValue(column, row, values[i]);
            if(validator.cellIsValid(potentialSolution, column, row)){
                if(recursivelySolve(unsolvedBoard, potentialSolution, nextColumn, nextRow)){
                    return true;
                }
            }
        }
        
        // If no good value could be found for this cell, we return false. But
        // first we have to make sure we leave no trace of the values we have
        // written to the current cell, by writing zero to the current cell.
        potentialSolution.setValue(column, row, 0);
        return false;  
    }
    
    
    
    public static void main(String[] args) throws Exception{
        Board blank = new Board();
        Solver solver = new RandomBruteForceSolver();
        
        Board board1 = solver.solve(blank);
        board1.print();
        
        Board board2 = solver.solve(blank);
        board2.print();
        
        
    }
    
}
