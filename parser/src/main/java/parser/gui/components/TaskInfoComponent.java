package parser.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import parser.config.entities.TaskConfig;

public class TaskInfoComponent extends GridPane{
    
    private TextField firstPage=new TextField();
    private TextField lastPage=new TextField();
    private TextField dbName=new TextField();
    private TextField taskName=new TextField();
    private TextField baseUrl=new TextField();
    private TextField baseUrlForPages=new TextField();
    private TextField pagePrefix=new TextField();
    private TextField cityName=new TextField();
    private TextField pathToExcel=new TextField();

    public TaskInfoComponent(){
        
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25,25,25,25));
        add(new Text("Параметры задания"),0,0,2,1);
        add(firstPage,1,1);
        add(lastPage,1,2);
        add(dbName,1,3);
        add(taskName,1,4);
        add(baseUrl,1,5);
        add(baseUrlForPages,1,6);
        add(pagePrefix,1,7);
        add(cityName,1,8);
        add(pathToExcel,1,9);

        add(new Label("Первая страница"),0,1);
        add(new Label("Последняя страница"),0,2);
        add(new Label("База данных"),0,3);
        add(new Label( "Название задачи"),0,4);
        add(new Label("Адрес сайта"),0,5);
        add(new Label("Сcылка на результаты поиска"),0,6);
        add(new Label("Префикс для страницы"),0,7);
        add(new Label("Город"),0,8);
        add(new Label("Куда Сохранить"),0,9);

    }


    public void  fillWithData(TaskConfig taskConfig){
       
        firstPage.setText(Integer.toString(taskConfig.firstPage));
        lastPage.setText(Integer.toString(taskConfig.lastPage));
        dbName.setText(taskConfig.dbName);
        taskName.setText(taskConfig.taskName);
        baseUrl.setText(taskConfig.baseUrl);
        baseUrlForPages.setText(taskConfig.baseUrlForPages);
        pagePrefix.setText(taskConfig.pagePrefix);
        cityName.setText(taskConfig.cityName);
        pathToExcel.setText(taskConfig.pathToExcel);


    }





    
}
