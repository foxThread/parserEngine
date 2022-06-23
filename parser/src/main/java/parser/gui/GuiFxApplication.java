package parser.gui;





import javafx.application.Application;
import javafx.stage.Stage;
import parser.gui.scenes.TaskSceneBuilder;

public class  GuiFxApplication extends Application{


       
    
    private void generateGUI(Stage stage){
        
        
        TaskSceneBuilder taskSceneBuilder=new TaskSceneBuilder(stage);
        stage.setTitle("Сбор данных");
        stage.setScene(taskSceneBuilder.getScene());
        stage.show();
        
      
    }
    
   
   


    public static void main(String[] args){
        
       
        launch(args);  
 
    }
 
    @Override
    public void start(Stage stage) {

                  
        generateGUI(stage);
             
       
       
    }

    
}
