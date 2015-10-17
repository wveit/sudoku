package app;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;

public class MainApplication extends Application{
    SudokuGui gui = new SudokuGui();
    Game game = new Game();
    
    @Override
    public void start(Stage stage){
        game.setGui(gui);
        
        Scene scene = new Scene(gui);
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.show();
        
        gui.giveFocus();
        game.start();     
    }
    
//    @Override
//    public void stop(){
//        game.setGui(null);
//    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}
