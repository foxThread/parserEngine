package parser.gui.scenes;




import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import parser.abstractparsers.flatparser.entities.Flat;
import parser.config.AppConfig;
import parser.config.entities.TaskConfig;

import parser.gui.ProcessProgressBar;
import parser.report.FlatsReportTask;
import parser.typicalrepos.MongoRepository;
import parser.typicaltasks.jfx.MainParsingTask;


public class MainSceneBuilder{

    private Stage rootStage;
    private Scene scene;
    private MainParsingTask<Flat> parserTask=null;
    
   

    VBox progressSection=new VBox();
        
    ProcessProgressBar linksCollectorBar=new ProcessProgressBar("Сбор ссылок");
    ProcessProgressBar contentCollectorBar=new ProcessProgressBar("Сбор контента");
    ProcessProgressBar contentParserBar=new ProcessProgressBar("Парсинг контента");
    ProcessProgressBar reportBar=new ProcessProgressBar("Отчет");
    Button startMissionButton=new Button("Отработка задания");
    Button startReportButton=new Button("Подготовка отчета");
    CheckBox parsingOnlyVariant=new CheckBox("Только парсинг(Если контент уже в базе)");
      

  
    
    public MainSceneBuilder(Stage rootStage,TaskConfig taskConfig){
               

        this.rootStage=rootStage;
              
        progressSection.setSpacing(10);
        progressSection.setAlignment(Pos.BASELINE_LEFT);
        
        progressSection.getChildren().add(linksCollectorBar);
        progressSection.getChildren().add(contentCollectorBar);
        progressSection.getChildren().add(contentParserBar);
        progressSection.getChildren().add(reportBar);
        
        HBox startMissionBox=new HBox();
        startMissionBox.getChildren().add(startMissionButton);
        startMissionBox.getChildren().add(parsingOnlyVariant);

        progressSection.getChildren().add(startMissionBox);
        progressSection.getChildren().add(startReportButton);

       // progressSection.getChildren().add(FxBrowser.getInstance());
        parserTask=AppConfig.getTask(taskConfig);
      
        startMissionButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){               
                    
             if(!parserTask.isRun()){
                          
                parserTask.setParsingOnlyVariant(parsingOnlyVariant.isSelected());
                parserTask.setLinksLoaderBar(linksCollectorBar); 
                parserTask.setContentLoaderBar(contentCollectorBar);
                parserTask.setObjectsParserBar(contentParserBar);
                   
                new Thread(parserTask).start();
                System.out.println("Task started");
             }    
           
          }});


          startReportButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){               
                    
             FlatsReportTask task=new FlatsReportTask(new MongoRepository<Flat>(taskConfig.dbName,taskConfig.taskName,Flat.class), taskConfig);
             reportBar.bindTask(task);
             new Thread(task).start();
           
          }});

          this.scene=new Scene(progressSection);



    }


    public Scene getScene(){
        return scene;
        
    }
    
}
