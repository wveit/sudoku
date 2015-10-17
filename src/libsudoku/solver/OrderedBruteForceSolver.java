package libsudoku.solver;

import libsudoku.validator.Validator;
import libsudoku.validator.BasicValidator;
import libsudoku.board.Board;
import libsudoku.fileworker.*;


public class OrderedBruteForceSolver implements Solver{
    
    public OrderedBruteForceSolver(){ 
        validator = new BasicValidator();
    }
    
    public OrderedBruteForceSolver(Validator validator){
        this.validator = validator;
    }
    
    @Override
    public Board solve(Board unsolvedBoard){
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
    
    
    private Validator validator;
    
    
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
        for(int i = 1; i <=9; i++){
            potentialSolution.setValue(column, row, i);
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
        Board board = new Board();
        FileWorker fw = new BasicFileWorker();
        fw.readFromFile("puzzle1.txt", board);
        //board.setValue(1, 1, 9);
        System.out.println("Puzzle --->");
        board.print();
        
        
        
        Solver solver = new OrderedBruteForceSolver();
        Board solution = solver.solve(board);
        System.out.println("Solution --->");
        
        if(solution != null){
            solution.print();
            
            Board realSolution = new Board();
            fw.readFromFile("solution1.txt", realSolution);

            if(solution.equals(realSolution)){
                System.out.println("match");
            }
            else{
                System.out.println("no match");
            }
        }
        else{
            System.out.println("No solution");
        }
        
        
                
    }
    
}
