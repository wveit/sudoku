package libsudoku.fileworker;

import libsudoku.board.Board;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import java.util.InputMismatchException;
import libsudoku.BadSudokuFileException;
import java.io.FileNotFoundException;


public class BasicFileWorker implements FileWorker{
    
    @Override
    public void readFromFile(String filename, Board board) throws BadSudokuFileException, FileNotFoundException{
        // Open scanner for file to be read from. If file does not
        // exist, throw a new FileNotFoundException, ending this function
        File file = new File(filename); 
        Scanner scanner = null;
        
        // Scanner constructor may throw FileNotFoundException
        try{
            scanner = new Scanner(file);
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("sudoku.Board.readFromFile(String filename) could not find file: " + filename);
        }
        
        // Load data from file
        // If the sudoku file is bad for some reason, throw BadSudokuFileException
        try{
            for(int row = 0; row < 9; row++){
                for(int column = 0; column < 9; column++){
                    board.setValue(column, row, scanner.nextInt());
                }
            }
        }
        // IllegalArgumentException could come from setValue() and InputMismatchException could come from scanner.nextInt()
        catch(IllegalArgumentException | InputMismatchException e){
            throw new BadSudokuFileException("sudoku.Board.readFromFile(String filename) attempted to load a bad sudoku file");
        }
        finally{
            scanner.close();
        }
        
    }
    
    @Override
    public void writeToFile(String filename, Board board) throws FileNotFoundException{
        try(PrintWriter writer = new PrintWriter(filename)){ 
            writer.println(board);
        }
    }
    
    public static void main(String[] args) throws Exception{
        Board board = new Board();
        FileWorker fw = new BasicFileWorker();
        
        fw.readFromFile("puzzle1.txt", board);
        
        board.print();
    }

}
