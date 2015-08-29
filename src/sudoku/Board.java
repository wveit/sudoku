package sudoku;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Board {
    private int[][] values = new int[9][9];
    
    public Board(){}
    
    public Board(Board board){
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                values[col][row] = board.values[col][row];
            }
        }
    }
    
    public int getValue(int column, int row){
        return values[column][row];
    }
    
    public void setValue(int column, int row, int value){
        values[column][row] = value;
    }
    
    public void readFromFile(String filename){
        // Open scanner for file to be read from. If file does not
        // exist, print error message and return from function
        // without doing anything.
        File file = new File(filename); 
        Scanner scanner;
        try{    
            scanner = new Scanner(file);
        }
        catch(FileNotFoundException e){
            System.out.println("sudoku.Board.readFromFile( " + filename + " ) -> File not found.");
            return;
        }
        
        // Load data from file
        // CAUTION!!! This code assumes good file! Need to fix.
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                setValue(col, row, scanner.nextInt());
            }
        }
        
        // Close up the scanner
        scanner.close();
    }
    
    public void print(){
        for(int row = 0; row < 9; row++){
            if(row % 3 == 0)
                System.out.println("---------------------------------------");
            
            for(int col = 0; col < 9; col++){
                if(col %  3 == 0)
                    System.out.print(" | ");
                else
                    System.out.print("   ");
                System.out.print(values[col][row]);
            }
            
            System.out.println(" |");
        }
        System.out.println("---------------------------------------");
    }
    
    public boolean isValid(){
        // test each square's validity, return false upon reaching first invalid
        // square
        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                if(!squareIsValid(col, row))
                    return false;
            }
        }
        
        // if all squares were valid, board is valid, return true
        return true;
    }
    
    public boolean equals(Board other){
        for(int c = 0; c < 9; c++){
            for(int r = 0; r < 9; r++){
                if(values[c][r] != other.values[c][r])
                    return false;
            }
        }
        
        return true;
    }
    
    public boolean squareIsValid(int column, int row){
        // !!! Make this function more readable please (use testColumn and testRow everywhere too)
        int value = values[column][row];
        
        // if value is zero --> valid because no guess has been made yet.
        if(value == 0)
            return true;
        
        // if value is not 1-9 --> not valid
        if(value < 1 || value > 9)
            return false;
        
        // if value is not unique in the row --> not valid
        for(int c = 0; c < 9; c++){
            if(c != column && value == values[c][row]){
                return false;
            }
        }
        
        // if value is not unique in the column --> not valid
        for(int r = 0; r < 9; r++){
            if(r != row && value == values[column][r]){
                return false;
            }
        }
        
        // if value is not unique in the box --> not valid
        for(int testColumn = column / 3 * 3, endColumn = testColumn + 2; testColumn <= endColumn; testColumn++){
            for(int testRow = row / 3 * 3, endRow = testRow + 2; testRow <= endRow; testRow++){
                if(value == values[testColumn][testRow] && !(testColumn == column && testRow == row)){
                    return false;
                }
            }
        }
        
        // if we made it this far --> valid
        return true;
    }
    
    public static void main(String[] args){
        Board board = new Board();
        board.readFromFile("solution1.txt");
        board.print();
        
        if(board.isValid())
            System.out.println("Board is valid");
        else
            System.out.println("Board is not valid");
    }
}


