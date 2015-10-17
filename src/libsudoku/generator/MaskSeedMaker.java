package libsudoku.generator;

import libsudoku.board.Board;
import libsudoku.Difficulty;

public class MaskSeedMaker{
    
    
    public Board getMaskSeed(Difficulty difficulty){
        switch(difficulty){
            case EASY:
                return easyMask();
            case MEDIUM:
                return mediumMask();
            case HARD:
                return hardMask();
        }
        
        return new Board();
    }
    
    public Board hardMask(){
        Board board = mediumMask();
        
        board.setValue(4, 1, 1);
        board.setValue(2, 7, 1);
        board.setValue(4, 4, 1);
        board.setValue(2, 3, 1);
        
        return board;
    }
    
    public Board mediumMask(){
        Board board = easyMask();
        
        board.setValue(0, 7, 1);
        board.setValue(1, 8, 1);
        board.setValue(6, 1, 1);
        board.setValue(4, 6, 1);
        
        return board;
    }
    
    public Board easyMask(){
        Board board = new Board();
        
        board.setValue(1, 1, 1);
        board.setValue(1, 4, 1);
        board.setValue(1, 5, 1);
        board.setValue(1, 7, 1);
        board.setValue(2, 5, 1);
        board.setValue(3, 7, 1);
        board.setValue(4, 6, 1);
        board.setValue(5, 0, 1);
        board.setValue(5, 5, 1);
        board.setValue(6, 3, 1);
        board.setValue(6, 8, 1);
        board.setValue(7, 6, 1);
        board.setValue(7, 7, 1);
        board.setValue(7, 8, 1);
        board.setValue(8, 2, 1);
        board.setValue(8, 4, 1);
        board.setValue(8, 5, 1);
        board.setValue(8, 8, 1);
        board.setValue(6, 6, 1);
        board.setValue(0, 6, 1);
        board.setValue(3, 1, 1);
        board.setValue(3, 2, 1);
        board.setValue(6, 0, 1);
        board.setValue(7, 1, 1);
        board.setValue(3, 3, 1);
        board.setValue(4, 3, 1);
        board.setValue(3, 4, 1);
        board.setValue(0, 0, 1);
        board.setValue(1, 2, 1);
        board.setValue(5, 8, 1);
        board.setValue(4, 1, 1);
        board.setValue(2, 7, 1);
        board.setValue(4, 4, 1);
        board.setValue(2, 3, 1);
        board.setValue(0, 3, 1);
        board.setValue(6, 2, 1);
        
        return board;
    }
    
    public static void main(String[] args){
        MaskSeedMaker sm = new MaskSeedMaker();
        Board board = sm.getMaskSeed(Difficulty.EASY);
        board.print();
    }
}
