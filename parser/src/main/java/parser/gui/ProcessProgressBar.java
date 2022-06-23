package parser.gui;

import javafx.scene.layout.HBox;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.concurrent.Task;
import javafx.geometry.Insets;












public class ProcessProgressBar  extends HBox{

    private Text text;
    private ProgressBar bar;
    private ProgressIndicator indicator;

    
    
    
    public ProcessProgressBar(String text){
       // setAlignment(Pos.BASELINE_LEFT);
        setPadding(new Insets(25,25,25,25));
        
        
        
        
        bar=new ProgressBar(0);
        indicator=new ProgressIndicator(0);

        setPadding(new Insets(10,10,10,10));
        setSpacing(10);

        this.text = new Text(text);
        this.text.setWrappingWidth(100);
        bar.setPrefWidth(200);

        
              
        getChildren().add(this.text);
        getChildren().add(bar);
        getChildren().add(indicator);


    }

    public void bindTask (Task <Void> task){
        bar.progressProperty().bind(task.progressProperty());
        indicator.progressProperty().bind(task.progressProperty());
    }
    




}
