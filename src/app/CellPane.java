package app;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public final class CellPane extends StackPane{
    
    private boolean isHighlighted = false;
    int value = 0;
    private Label label = new Label();
    private static Font font = new Font(32);
    private static Font fixedFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 42);
    private CellClickListener clickListener = null;
    private boolean isFixed = false;
    
    public CellPane(){
        setMaxSize(80, 80);
        setMinSize(80, 80);
        label.setFont(font);
        this.getChildren().add(label);
        
        setHighlight(isHighlighted); 
        
        setOnMouseClicked(e->onClick());
    }
    
    public boolean isFixed(){
        return isFixed;
    }
    
    public void setIsFixed(boolean isFixed){
        this.isFixed = isFixed;
        if(isFixed){
            label.setFont(fixedFont);
            label.setStyle("-fx-text-fill:blue");
        }
        else{
            label.setFont(font);
            label.setStyle("-fx-text-fill:black");
        }
    }
    
    public void setHighlight(boolean isSet){
        isHighlighted = isSet;
        if(isSet){
            this.setStyle("-fx-border-color:green; -fx-border-width:5px");
        }
        else{
            this.setStyle("-fx-border-color:black; -fx-border-width:1px");
        }
    }
    
    public void setValue(int value){
        if(isFixed)
            return;
        
        this.value = value;
        if(value == 0)
            label.setText("");
        else
            label.setText(Integer.toString(value));
    }
    
    public int getValue(){
        return value;
    }
    
    public boolean isHighlighted(){
        return isHighlighted;
    }
    
    public void setClickListener(CellClickListener listener){
        this.clickListener = listener;
    }
    
    private void onClick(){
        if(clickListener != null)
            clickListener.onCellClicked(this);
    }
    
}
