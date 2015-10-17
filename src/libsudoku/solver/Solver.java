package libsudoku.solver;

import libsudoku.board.Board;
import java.util.ArrayList;

public interface Solver {
    public Board solve(Board unsolvedBoard);
    //public ArrayList<Board> solveAllSolutions(Board unsolvedBoard);
    //public ArrayList<Board> solveAllSolutions(Board unsolvedBoard, int solutionLimit);
}
