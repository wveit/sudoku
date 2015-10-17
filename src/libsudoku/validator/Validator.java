package libsudoku.validator;

import libsudoku.board.Board;
import java.util.List;
import libsudoku.Coord;

public interface Validator {
    public boolean cellIsValid(Board board, int column, int row);
    public boolean boardIsValid(Board board);
    public List<Coord> makeConflictList(Board board);
}
