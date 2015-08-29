package sudoku;


public class Solver {
    private Board originalBoard = null;
    private Board potentialSolution = null;
    private boolean holdsSolution = false;
    
    public Solver(Board board){
        originalBoard = new Board(board);
    }
    
    public Board getSolution(){
        potentialSolution = new Board(originalBoard);
        
        holdsSolution = recursivelySolve(0, 0);
        
        if(holdsSolution)
            return potentialSolution;
        else
            return null;
    }
    
    private boolean recursivelySolve(int column, int row){        
        // set up one of the ending conditions, where the solver has 
        // successfully assigned a number to each cell
        if(row >= 9)
            return true;
        
        // determine next cell to solve for
        int nextColumn = column + 1;
        int nextRow = row;
        if(nextColumn >= 9){
            nextRow++;
            nextColumn = 0;
        }
        
        if(originalBoard.getValue(column, row) > 0 && originalBoard.getValue(column, row) < 10)
            return recursivelySolve(nextColumn, nextRow);
        
        for(int i = 1; i <=9; i++){
            potentialSolution.setValue(column, row, i);
            if(potentialSolution.squareIsValid(column, row)){
                if(recursivelySolve(nextColumn, nextRow)){
                    return true;
                }
            }
        }
        
        potentialSolution.setValue(column, row, 0);
        return false;  
    }
    
    public static void main(String[] args){
        Board board = new Board();
        board.readFromFile("puzzle1.txt");
        System.out.println("Puzzle --->");
        board.print();
        
        Solver solver = new Solver(board);
        Board solution = solver.getSolution();
        System.out.println("Solution --->");
        if(solution != null)
            solution.print();
        else
            System.out.println("No solution");
                
    }
    
}
