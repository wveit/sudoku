package libsudoku.generator;

import libsudoku.solver.RandomBruteForceSolver;
import libsudoku.solver.Solver;
import libsudoku.validator.Validator;
import libsudoku.validator.BasicValidator;
import libsudoku.board.Board;
import java.util.Random;

public class RandomBoardGenerator {
    private Validator validator = new BasicValidator();
    private Solver solver = new RandomBruteForceSolver();
    private final int NUM_RANDOM_CELLS = 10;
    
    // not well written... fix this
    public Board generate(){
        Board board = new Board();
        Random random = new Random();
        
        for(int i = 0; i < NUM_RANDOM_CELLS; i++){
            int column = random.nextInt(8);
            int row = random.nextInt(8);
            
            if(board.getValue(column, row) != 0){
                i--;
                continue;
            }
            
            int value = random.nextInt(8) + 1;
            
            for(int j = 0; j < 9; j++){
                board.setValue(column, row, value);
                if(validator.cellIsValid(board, column, row)){
                    break;
                }
                else{
                    value = nextValue(value);
                }
            }
        }
        
        board = solver.solve(board);
        
        if(board == null || !validator.boardIsValid(board)){
            System.out.println("in-valid board created");
        }
        
        return (board == null? new Board() : board);
    }
    
    private int nextValue(int currentValue){
        currentValue++;
        if(currentValue == 10){
            currentValue = 1;
        }
        return currentValue;
    }
    
    
    public static void main(String[] args){
        RandomBoardGenerator gen = new RandomBoardGenerator();
        Board board = gen.generate();
        board.print();
    }

}
