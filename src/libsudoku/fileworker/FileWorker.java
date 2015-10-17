package libsudoku.fileworker;

import libsudoku.board.Board;
import libsudoku.BadSudokuFileException;
import java.io.FileNotFoundException;

public interface FileWorker {
    public void readFromFile(String filename, Board board) throws BadSudokuFileException, FileNotFoundException;
    public void writeToFile(String filename, Board board) throws FileNotFoundException;
}
