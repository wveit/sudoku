package libsudoku.shuffle;

import libsudoku.validator.Validator;
import libsudoku.validator.BasicValidator;
import libsudoku.board.Board;
import java.util.Random;
import libsudoku.Difficulty;
import libsudoku.generator.RandomBoardGenerator;
import libsudoku.solver.*;
import libsudoku.generator.MaskSeedMaker;



public class BoardShuffler {
    
    public void setRandomMoveRange(int min, int max){
        minRandomMoves = min;
        maxRandomMoves = max;
    }
    
    public int getMinRandomMoves(){ return minRandomMoves; }
    public int getMaxRandomMoves(){ return maxRandomMoves; }
    
    public void swapColumns(Board board, int col1, int col2){
        if(col1 == col2)
            return;
        
        for(int i = 0; i < 9; i++){
            int temp = board.getValue(col1, i);
            board.setValue(col1, i, board.getValue(col2, i));
            board.setValue(col2, i, temp);
        }
    }
    
    public void swapRows(Board board, int row1, int row2){
        if(row1 == row2)
            return;
        
        for(int i = 0; i < 9; i++){
            int temp = board.getValue(i, row1);
            board.setValue(i, row1, board.getValue(i, row2));
            board.setValue(i, row2, temp);
        }
    }
    
    public void swap3x3Columns(Board board, int col1, int col2){
        if(col1 == col2)
            return;
        
        col1 = COORD_2_3X3[col1];
        col2 = COORD_2_3X3[col2];
        
        for(int i = 0; i < 3; i++, col1++, col2++){
            swapColumns(board, col1, col2);
        }
        
    }
    
    
    public void swap3x3Rows(Board board, int row1, int row2){
        if(row1 == row2)
            return;
        
        row1 = COORD_2_3X3[row1];
        row2 = COORD_2_3X3[row2];
        
        for(int i = 0; i < 3; i++, row1++, row2++){
            swapRows(board, row1, row2);
        }
    }
    
    public void shuffle(Board board){
        int numRandomMoves = random.nextInt(maxRandomMoves - minRandomMoves) + minRandomMoves;
        for(int i = 0; i < numRandomMoves; i++){
            randomShuffleOperation(board);
        }
    }
    
    public void randomShuffleOperation(Board board){
        ShuffleOperation op = randomOperation();
        
        switch(op){
            case COLUMN_SWAP:{
                int startOf3 = random.nextInt(3) * 3;
                int randCol1 = startOf3 + random.nextInt(3);
                int randCol2 = startOf3 + random.nextInt(3);
                swapColumns(board, randCol1, randCol2);
                break;
            }
            case ROW_SWAP:{
                int startOf3 = random.nextInt(3) * 3;
                int randRow1 = startOf3 + random.nextInt(3);
                int randRow2 = startOf3 + random.nextInt(3);
                swapRows(board, randRow1, randRow2);
                break;
            }
            case COLUMN_3X3_SWAP:{
                int rand1 = random.nextInt(3);
                int rand2 = random.nextInt(3);
                swap3x3Columns(board, rand1, rand2);
                break;
            }
            case ROW_3X3_SWAP:{
                int rand1 = random.nextInt(3);
                int rand2 = random.nextInt(3);
                swap3x3Rows(board, rand1, rand2);
                break;
            }
        }
    }
    
    private ShuffleOperation randomOperation(){
        ShuffleOperation[] array = ShuffleOperation.values();
        return array[random.nextInt(array.length)];
    }
    
    public static void main(String[] args){
        Board blank = new Board();
        Solver solver = new OrderedBruteForceSolver();
        BoardShuffler shuffler = new BoardShuffler();
        Validator validator = new BasicValidator();
        MaskSeedMaker seed = new MaskSeedMaker();
        
        //Board board = seed.getMaskSeed(Difficulty.EASY);
        Board board = new Board();
        board = solver.solve(board);
        board.print();
        shuffler.shuffle(board);
        board.print();
        if(validator.boardIsValid(board)){
            System.out.println("Valid");
        }
        else{
            System.out.println("Not Valid");
        }
        
        
    }

    private final static int[] COORD_2_3X3 = {0, 3, 6};
    private int minRandomMoves = 10000;
    private int maxRandomMoves = 10001;
    private Random random = new Random();
}
