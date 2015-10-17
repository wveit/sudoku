package app;
import libsudoku.Difficulty;
import libsudoku.board.Board;

import libsudoku.generator.*;
import libsudoku.shuffle.BoardShuffler;

public class PuzzleGetter {
    
    public Board getBoard(Difficulty difficulty){
        
        RandomBoardGenerator gen = new RandomBoardGenerator();
        BoardShuffler shuffler = new BoardShuffler();
        MaskSeedMaker maskSeed = new MaskSeedMaker();
        
        Board board = gen.generate();
        
        Board mask = maskSeed.getMaskSeed(difficulty);
        //shuffler.shuffle(mask);
        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(mask.getValue(column, row) != 0){
                    board.setValue(column, row, 0);
                }
            }
        }
        
        return board;
    }
    
}
