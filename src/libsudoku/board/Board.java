package libsudoku.board;

public class Board {
    
    public Board(){}
    
    public Board(Board other){
        privateCopy(other);
    }
    
    public int getValue(int column, int row){
        return values[column][row];
    }
    
    public void setValue(int column, int row, int value){
        if(value < 0 || value > 9){
            throw new IllegalArgumentException("sudoku.Board.setValue(int column, int row, int value) illegal value: " + value);
        }
        
        values[column][row] = value;
    }
    
    public void copy(Board other){
        privateCopy(other);
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                builder.append(values[column][row]);
                builder.append(' ');
            }
            builder.append('\n');
        }
        
        return builder.toString();
    }
    
    public void print(){
        for(int row = 0; row < 9; row++){
            if(row % 3 == 0)
                System.out.println("---------------------------------------"); 
            
            for(int column = 0; column < 9; column++){
                if(column %  3 == 0)
                    System.out.print(" | ");
                else
                    System.out.print("   ");
                System.out.print(values[column][row]);
            }
            
            System.out.println(" |");
        }
        System.out.println("---------------------------------------");
    }
    
    @Override
    public boolean equals(Object o){
        // Check if they are the same object
        if(this == o)
            return true;
        
        // Check if they are not even the same type
        if(!(o instanceof Board))
            return false;
        
        // Cast o to a Board object, and then check for equality 
        Board other = (Board)o;
            
        for(int column = 0; column < 9; column++){
            for(int row = 0; row < 9; row++){
                if(values[column][row] != other.values[column][row])
                    return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode(){
        int result = 17;
        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                result = 31 * result + values[column][row];
            }
        }
        
        return result;
    }
    
    public boolean isFull(){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(values[column][row] == 0){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public boolean isEmpty(){        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(values[column][row] != 0){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    
    /*  -------------  Private ------------------ */
    
    private final int[][] values = new int[9][9];
    
    private final void privateCopy(Board other){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                values[column][row] = other.values[column][row];
            }
        }
    }
}