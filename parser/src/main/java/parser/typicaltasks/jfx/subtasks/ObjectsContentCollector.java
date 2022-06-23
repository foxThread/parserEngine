package parser.typicaltasks.jfx.subtasks;


import java.util.ArrayList;

import javafx.concurrent.Task;

import parser.entities.SavedPage;
import parser.exceptions.HtmlLoadException;
import parser.exceptions.ObjectSavingException;
import parser.htmlgetters.HtmlGetter;
import parser.interfaces.ObjectsRepository;
import parser.typicaltasks.jfx.MainParsingTask;
import parser.utils.Threads;

public class ObjectsContentCollector<T>  extends Task<Void>{

    private ObjectsRepository<T> repo=null;
    private HtmlGetter htmlGetter=null;


    private int  numberOfObjects=0;
    private int  currentObjectIndex=0;

    private volatile boolean isWorking=false;


    public boolean isAlive(){
        return isWorking;
    }

    
    public ObjectsContentCollector(MainParsingTask<T> parentTask){
        isWorking=true;
        this.repo=parentTask.getRepo();
        htmlGetter=parentTask.getHtmlGetter();


    }
    
    
    public Void call(){
        
        start();
        isWorking=false;
        return null;

    }


    private void start(){
        ArrayList<SavedPage> links=repo.getAllLinks();
        numberOfObjects=links.size();
       
        currentObjectIndex=0;

        updateProgress(0, numberOfObjects);

        for (SavedPage link:links){
            
            if(isCancelled()){
                break;
            }
            
            if (!repo.isContentLoaded(link.url)){
                SavedPage page=new SavedPage();
                page.url=link.url; 
               
                try{
                    
                    
                    System.out.println("getting html");
                    page.content=htmlGetter.getHtml(link.url);
                    
                    System.out.println("saving content");
                    repo.addContent(page);            
                    
                    System.out.println("making pause");
                    Threads.pauseThread(3,5);
                } catch(HtmlLoadException e){
                    System.out.println("HtmlLoadException");
                    e.printStackTrace();
                } catch(ObjectSavingException e){
                    System.out.println("ObjectSavingException");
                    e.printStackTrace();
                } catch(Exception e){
                    System.out.println("oo..oooyyyy");
                    e.printStackTrace();

                }
            }
            updateProgress(++currentObjectIndex, numberOfObjects);



        }

    }
    




}
