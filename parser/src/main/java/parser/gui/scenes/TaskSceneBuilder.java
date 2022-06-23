package parser.gui.scenes;

import java.io.File;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.config.entities.TaskConfig;
import parser.gui.components.TaskInfoComponent;




public class TaskSceneBuilder{

    private Stage rootStage;
    private Scene scene;
    TaskConfig taskConfig=null;
    
   

    
    private Button chooseFileButton=new Button("Открыть файл задания");
    private Button startButton=new Button("Приступить к выполнению");
    private TaskInfoComponent taskInfoComponent=new TaskInfoComponent();

    

   


   
    

   
    
    
    
    public TaskSceneBuilder(Stage rootStage){
        
        

        this.rootStage=rootStage;
       
        VBox form=new VBox();
        form.setSpacing(10);
        form.setAlignment(Pos.CENTER);
        
        form.getChildren().add(taskInfoComponent);
        form.getChildren().add(chooseFileButton);
        form.getChildren().add(startButton);
        
        

        chooseFileButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){
                
                FileChooser fileChooser = new FileChooser();
                File configFile=fileChooser.showOpenDialog(rootStage);
               
                taskConfig= new TaskConfig(configFile.getAbsolutePath());
                taskInfoComponent.fillWithData(taskConfig);

                }
          });


          startButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){
                if(taskConfig !=null){
                    rootStage.setScene(new MainSceneBuilder(rootStage, taskConfig).getScene());
                }

                
               

                }
          });


          this.scene=new Scene(form);



    }


    public Scene getScene(){
       
        return scene;
        
    }
    
}
