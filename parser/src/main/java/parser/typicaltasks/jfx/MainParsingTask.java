package parser.typicaltasks.jfx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import parser.gui.ProcessProgressBar;
import parser.htmlgetters.HtmlGetter;
import parser.interfaces.ObjectParser;
import parser.interfaces.ObjectsRepository;

import parser.typicaltasks.jfx.subtasks.*;

public class MainParsingTask<T> extends Task<Void> {

    @Getter
    @Setter
    private ObjectParser<T> parser;
    
    @Getter
    @Setter
    private ObjectsRepository<T> repo;
    
    @Getter
    @Setter
    private HtmlGetter htmlGetter;

    @Getter
    @Setter
    private ProcessProgressBar linksLoaderBar=null;

    @Getter
    @Setter
    private ProcessProgressBar contentLoaderBar=null;

    @Getter
    @Setter
    private ProcessProgressBar objectsParserBar=null;

    @Getter
    @Setter
    private int firstPage=0;
    
    @Getter
    @Setter
    private int lastPage=0;
    
    @Getter
    @Setter
    private String baseUrl=null;
    
    @Getter
    @Setter
    private String baseUrlForPages=null;
    
    @Getter
    @Setter
    private String pagePrefix=null;

    @Getter
    @Setter
    private volatile boolean run=false;

    @Getter
    @Setter
    private boolean parsingOnlyVariant=false;

    @Getter
    @Setter
    private String pdfSavingPath=null;


     

   



  public   MainParsingTask( ObjectParser<T> parser,ObjectsRepository<T> repo, HtmlGetter htmlGetter){
        this.parser=parser;
        this.repo=repo;
        this.htmlGetter=htmlGetter;
    }

    public Void call(){

        
        if (linksLoaderBar==null || contentLoaderBar==null || objectsParserBar==null){
            return null;
        }

        run=true;

        if (parsingOnlyVariant){

            ContentParser<T> contentParser=new ContentParser<T>(this);
            new Thread(contentParser).start();
      
            Platform.runLater( new Runnable(){
              @Override
              public void run(){
                  objectsParserBar.bindTask(contentParser);
              }
      
          });

          return null;

        }
        WebPagesLinksCollector<T> linksCollector=new WebPagesLinksCollector<T>(this);
        linksCollector.setParameters(firstPage, lastPage);
        
        new Thread(linksCollector).start();

        Platform.runLater( new Runnable(){
            @Override
            public void run(){
                linksLoaderBar.bindTask(linksCollector);
            }

        });

       while(linksCollector.isAlive()){}

     
       ObjectsContentCollector<T> contentCollector=new ObjectsContentCollector<T>(this);
       new Thread(contentCollector).start();

       Platform.runLater( new Runnable(){
        @Override
        public void run(){
            contentLoaderBar.bindTask(contentCollector);
        }

      });

      while(contentCollector.isAlive()){}
      
      ContentParser<T> contentParser=new ContentParser<T>(this);
      new Thread(contentParser).start();

      Platform.runLater( new Runnable(){
        @Override
        public void run(){
            objectsParserBar.bindTask(contentParser);
        }

    });
   







        
        
        
        
        run=false;
        return null;
       

        





     


    
    
    
    
    }





    
    
    
}
