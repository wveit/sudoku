package app;
import javafx.event.EventType;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class MainMenu extends VBox{
    
    public enum Buttons{ 
        NEW_GAME, EASY, MEDIUM, HARD, VALIDATE, SUBMIT, SOLVE, EXIT, NUM_BUTTONS
    }
    
    public MainMenu(){
        initButtons();
        
    }

    public void showDifficultyButtons(boolean shouldShow){
        showingDifficultyButtons = shouldShow;
        
        if(shouldShow){
            getChildren().add(1, buttonArray[Buttons.EASY.ordinal()]);
            getChildren().add(2, buttonArray[Buttons.MEDIUM.ordinal()]);
            getChildren().add(3, buttonArray[Buttons.HARD.ordinal()]);
        }
        else{
            getChildren().remove(buttonArray[Buttons.EASY.ordinal()]);
            getChildren().remove(buttonArray[Buttons.MEDIUM.ordinal()]);
            getChildren().remove(buttonArray[Buttons.HARD.ordinal()]);
        }
        
    }
    
    public void setButtonAction(Buttons button, Action action){
        buttonArray[button.ordinal()].setOnAction(e->action.run());
    }
    
    /*   ---------    Private    ----------------- */
    
    private Button[] buttonArray = new Button[Buttons.NUM_BUTTONS.ordinal()];
    private boolean showingDifficultyButtons = false;
        
    private void initButtons(){
        buttonArray[Buttons.NEW_GAME.ordinal()] = new Button("New Game");
        buttonArray[Buttons.EASY.ordinal()] = new Button("Easy");
        buttonArray[Buttons.MEDIUM.ordinal()] = new Button("Medium");
        buttonArray[Buttons.HARD.ordinal()] = new Button("Hard");
        buttonArray[Buttons.VALIDATE.ordinal()] = new Button("Validate Puzzle");
        buttonArray[Buttons.SUBMIT.ordinal()] = new Button("Submit Complete Puzzle");
        buttonArray[Buttons.SOLVE.ordinal()] = new Button("Solve Puzzle");
        buttonArray[Buttons.EXIT.ordinal()] = new Button("Exit");

        buttonArray[Buttons.NEW_GAME.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.EASY.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.MEDIUM.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.HARD.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.VALIDATE.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.SUBMIT.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.SOLVE.ordinal()].setMaxWidth(Double.MAX_VALUE);
        buttonArray[Buttons.EXIT.ordinal()].setMaxWidth(Double.MAX_VALUE);

        buttonArray[Buttons.EASY.ordinal()].setStyle("-fx-background-color:darkgray; -fx-border-color:black");
        buttonArray[Buttons.MEDIUM.ordinal()].setStyle("-fx-background-color:darkgray; -fx-border-color:black");
        buttonArray[Buttons.HARD.ordinal()].setStyle("-fx-background-color:darkgray; -fx-border-color:black");

        getChildren().addAll(buttonArray);
        showDifficultyButtons(false);

        buttonArray[Buttons.NEW_GAME.ordinal()].setOnAction(e->{
            showingDifficultyButtons = !showingDifficultyButtons;
            showDifficultyButtons(showingDifficultyButtons);
        });
    }
}
