package app;

import com.sun.glass.ui.Application;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;

public class SudokuGui extends StackPane implements EventHandler<KeyEvent>{
    
    private Game game;
    private BoardPane boardPane;
    private MainMenu menu = new MainMenu();
    private Label messageLabel = new Label();
    private CellChangeAction cellChangeRequestHandler;
    
    public SudokuGui(){
        HBox hbox = new HBox();
        hbox.getChildren().add(menu);
        boardPane = new BoardPane(this);
        hbox.getChildren().add(boardPane);
        this.setOnKeyPressed(this);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox, messageLabel);
        this.getChildren().add(vbox);
    }
    
    public void close(){
        this.getScene().getWindow().hide();
    }
    
    public void giveFocus(){
        this.requestFocus();
    }
    
    public void resetBoard(){
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                boardPane.setIsCellFixed(column, row, false);
                boardPane.setCellValue(column, row, 0);
            }
        }
    }
    
    public int getCellValue(int column, int row){
        return boardPane.getCellValue(column, row);
    }
    
    public void setCellValue(int column, int row, int value){
        boardPane.setCellValue(column, row, value);
    }
    
    public boolean isCellFixed(int column, int row){
        return boardPane.isCellFixed(column, row);
    }
    
    public void setIsCellFixed(int column, int row, boolean isFixed){
        boardPane.setIsCellFixed(column, row, isFixed);
    }
    
    public void writeMessage(String message){
        messageLabel.setText(message);
    }
    
    public void markConflictingCell(int column, int row){
        // ....
    }
    
    @Override
    public void handle(KeyEvent e){
        KeyCode code = e.getCode();
        
        if(code.isDigitKey()){
            int newValue = Integer.parseInt(code.getName());
            cellChangeRequestHandler.run(boardPane.getHighlightColumn(), boardPane.getHighlightRow(), newValue);
        }
    }

    
    public void setCellChangeRequestHandler(CellChangeAction coordAction){
        cellChangeRequestHandler = coordAction;
    }
    
    public void setMenuAction(MainMenu.Buttons button, Action action){
        menu.setButtonAction(button, action);
    }
    
    
    
}
