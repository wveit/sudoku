package libsudoku.validator;

import libsudoku.board.Board;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import libsudoku.BadSudokuFileException;
import libsudoku.Coord;
import libsudoku.fileworker.*;


public class BasicValidator implements Validator{
    
    public BasicValidator(){}

    @Override
    public boolean cellIsValid(Board board, int column, int row){
        
        int value = board.getValue(column, row);
        
        // if value is zero --> valid because no guess has been made yet.
        if(value == 0)
            return true;
        
        // if value is not 1-9 --> not valid
        if(value < 1 || value > 9)
            return false;
        
        
        // if value is not unique in the row --> not valid
        for(int testColumn = 0; testColumn < 9; testColumn++){
            if(testColumn != column && value == board.getValue(testColumn, row)){
                return false;
            }
        }
        
        
        
        // if value is not unique in the column --> not valid
        for(int testRow = 0; testRow < 9; testRow++){
            if(testRow != row && value == board.getValue(column, testRow)){
                return false;
            }
        }
        
        
        // if value is not unique in the 3x3 box --> not valid
        for(int testColumn = column / 3 * 3, endColumn = testColumn + 2; testColumn <= endColumn; testColumn++){
            for(int testRow = row / 3 * 3, endRow = testRow + 2; testRow <= endRow; testRow++){
                if(value == board.getValue(testColumn, testRow) && !(testColumn == column && testRow == row)){
                    return false;
                }
            }
        }

        // if we made it this far --> valid
        return true;
    }
    
    @Override
    public boolean boardIsValid(Board board){
        
        // test each square's validity, return false upon reaching first invalid
        // square
        for(int column = 0; column < 9; column++){
            for(int row = 0; row < 9; row++){
                if(!cellIsValid(board, column, row))
                    return false;
            }
        }
        
        // if all squares were valid, board is valid, return true
        return true;
    }
    
    @Override
    public List<Coord> makeConflictList(Board board){
        ArrayList<Coord> conflicts = new ArrayList<>();
        
        // test each square's validity, return false upon reaching first invalid
        // square
        for(int column = 0; column < 9; column++){
            for(int row = 0; row < 9; row++){
                if(!cellIsValid(board, column, row))
                    conflicts.add(new Coord(column, row));
            }
        }
        
        return conflicts;
    }
    
    public static void main(String[] args) throws BadSudokuFileException, FileNotFoundException{
        Board board = new Board();
        FileWorker fw = new BasicFileWorker();
        fw.readFromFile("puzzle1.txt", board);
        board.setValue(8, 7, 1);
        board.print();
        
        Validator v = new BasicValidator();
        if(v.boardIsValid(board)){
            System.out.println("valid");
        }
        else{
            System.out.println("not valid");
        }
        
    }
}
