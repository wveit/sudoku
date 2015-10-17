package app;
import libsudoku.Coord;
import javafx.scene.layout.*;

public final class BoardPane extends Pane implements CellClickListener{
    
    private SudokuGui sudokuGui;
    private CellPane[][] cells = new CellPane[9][9];
    private int highlightColumn = 0;
    private int highlightRow = 0;
        
    public BoardPane(SudokuGui sudokuGui){
        this.sudokuGui = sudokuGui;
        
        int yPos = 0;
        for(int row = 0; row < 9; row++){
            int xPos = 0;
            for(int column = 0; column < 9; column++){
                cells[column][row] = new CellPane();
                cells[column][row].setLayoutX(xPos);
                cells[column][row].setLayoutY(yPos);
                getChildren().add(cells[column][row]);
                cells[column][row].setClickListener(this); // this is bad! memory leak! figure out a way around this
                xPos += 80;
                if((column + 1) % 3 == 0)
                    xPos += 20;
            }
            
            yPos += 80;
            if((row + 1) % 3 == 0)
                yPos += 20;
        }
        
        setHighlight(0, 0);
    }
    
    public int getHighlightColumn(){
        return highlightColumn;
    }
    
    public int getHighlightRow(){
        return highlightRow;
    }
    
    
    public void setHighlight(int column, int row){
        cells[highlightColumn][highlightRow].setHighlight(false);
        highlightColumn = column;
        highlightRow = row;
        cells[highlightColumn][highlightRow].setHighlight(true);
    }
    
    public int getCellValue(int column, int row){
        return cells[column][row].getValue();
    }
    
    public void setCellValue(int column, int row, int value){
        cells[column][row].setValue(value);
    }
    
    public boolean isCellFixed(int column, int row){
        return cells[column][row].isFixed();
    }
    
    public void setIsCellFixed(int column, int row, boolean isFixed){
        cells[column][row].setIsFixed(isFixed);
    }
    
    @Override
    public void onCellClicked(CellPane cellPane){
        Coord coord = findCoords(cellPane);
        if(coord.column >= 0){
            setHighlight(coord.column, coord.row);
        }
    }
    
    private Coord findCoords(CellPane cellPane){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                if(cells[column][row] == cellPane){
                    return new Coord(column, row);
                }                
            }
        }
        
        return new Coord(-1, -1);
    }
    
}
