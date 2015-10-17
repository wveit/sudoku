package app;

import libsudoku.Difficulty;
import libsudoku.Coord;
import libsudoku.solver.OrderedBruteForceSolver;
import libsudoku.solver.Solver;
import libsudoku.validator.Validator;
import libsudoku.validator.BasicValidator;
import libsudoku.board.Board;
import java.util.List;
import libsudoku.shuffle.BoardShuffler;


public class Game {
    
    private Board puzzleBoard;
    private Board playerBoard;
    private SudokuGui gui;
    private Validator validator;
    private PuzzleGetter puzzleGetter = new PuzzleGetter();
    
    public void start(){
        // if gui hasn't been set, display warning message and return
        if(gui == null){
            System.out.println("Error, tried to call Game.start() without "
            + "registering GUI");
            return;
        }
        
        // load blank puzzleboard and playerboard
        puzzleBoard = new Board();
        playerBoard = new Board();
        
        // Set a validator
        validator = new BasicValidator();
        
        
    }
    
    public void setGui(SudokuGui gui){
        // update the reference 'gui'
        this.gui = gui;
        
        // register this game with the GUI so that the game can have its onXXX()
        // methods called
        this.setUpHandlers();
    }
    
    
    private void onNewGameRequest(Difficulty difficulty){
        // Load a new puzzle of desired difficulty into the puzzleBoard
        puzzleBoard = puzzleGetter.getBoard(difficulty);
            
        // Copy contents of puzzleBoard into playerBoard
        playerBoard = new Board(puzzleBoard);
        
        // Reset the GUI board
        gui.resetBoard();
        
        // Load filled cells from puzzleBoard into the GUI as fixed cells
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(puzzleBoard.getValue(column, row) != 0){
                    gui.setCellValue(column, row, puzzleBoard.getValue(column, row));
                    gui.setIsCellFixed(column, row, true);
                }
            }
        }
        
    }
    
    public void onGuiCellChangeRequested(int column, int row, int newValue){
        // Test if cell is fixed -> if fixed return from function because we
        // cannot update this cell
        if(gui.isCellFixed(column, row)){
            return;
        }
        
        // Test if new value at (column, row) is valid.
        if(newValue >= 0 && newValue <= 9){
            // If valid, update the value to playerBoard and gui.
            playerBoard.setValue(column, row, newValue);
            gui.setCellValue(column, row, newValue);
        }
    }
    
    private void onSubmitRequest(){
        // Test if board has been filled
        if(!playerBoard.isFull()){
            gui.writeMessage("Submit failed: Puzzle has not been completed");
            return;
        }
        
        // Test if the board is valid and get list of conflict cells.
        List<Coord> conflicts = validator.makeConflictList(playerBoard);
        if(conflicts.isEmpty()){
            gui.writeMessage("Submit successful: Correct solution found");
        }
        else{
            // If board is not valid -> Tell GUI to give "Invalid" message
            // while highlighting conflicting cells
            gui.writeMessage("Submit failed: Conflicts found");
            
            for(Coord conflict : conflicts){
                if(puzzleBoard.getValue(conflict.column, conflict.row) != 0){
                    gui.markConflictingCell(conflict.column, conflict.row);
                }
            }
        }
    }
    
    private void onSolveRequest(){
        // ...
        Solver solver = new OrderedBruteForceSolver();
        Board solution = solver.solve(playerBoard);
        
        if(solution == null){
            gui.writeMessage("No solution found");
            return;
        }
        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(playerBoard.getValue(column, row) == 0){
                    gui.setCellValue(column, row, solution.getValue(column, row));
                }
            }
        }
        
        playerBoard = solution;
    }
    
    private void onValidateRequest(){
        // ...
        // Test if the board is valid and get list of conflict cells.
        List<Coord> conflicts = validator.makeConflictList(playerBoard);
        if(conflicts.isEmpty()){
            gui.writeMessage("Validate successful: No conflicts found");
        }
        else{
            // If board is not valid -> Tell GUI to give "Invalid" message
            // while highlighting conflicting cells
            gui.writeMessage("Validate failed: Conflicts found");
            
            for(Coord conflict : conflicts){
                if(puzzleBoard.getValue(conflict.column, conflict.row) != 0){
                    gui.markConflictingCell(conflict.column, conflict.row);
                }
            }
        }
    }
    
    private void onExitRequest(){
        gui.close();
    }
    
    private void setUpHandlers(){
        gui.setMenuAction(MainMenu.Buttons.EASY, () -> {
            onNewGameRequest(Difficulty.EASY);
        });
        
        gui.setMenuAction(MainMenu.Buttons.MEDIUM, () -> {
            onNewGameRequest(Difficulty.MEDIUM);
        });
        
        gui.setMenuAction(MainMenu.Buttons.HARD, () -> {
            onNewGameRequest(Difficulty.HARD);
        });
        
        gui.setMenuAction(MainMenu.Buttons.VALIDATE, () -> {
            onValidateRequest();
        });
        
        gui.setMenuAction(MainMenu.Buttons.SUBMIT, () -> {
            onSubmitRequest();
        });
        
        gui.setMenuAction(MainMenu.Buttons.SOLVE, () -> {
            onSolveRequest();
        });
        
        gui.setMenuAction(MainMenu.Buttons.EXIT, ()->{
            onExitRequest();
        });
        
        gui.setCellChangeRequestHandler((int col, int row, int value)->{
            onGuiCellChangeRequested(col, row, value);
        });
    }

}
