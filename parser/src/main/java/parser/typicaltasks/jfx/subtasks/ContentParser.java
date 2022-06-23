package parser.typicaltasks.jfx.subtasks;

import javafx.concurrent.Task;

import parser.entities.SavedPage;
import parser.exceptions.ObjectSavingException;
import parser.exceptions.ParseErrorException;
import parser.interfaces.ObjectParser;
import parser.interfaces.ObjectsRepository;
import parser.typicaltasks.jfx.MainParsingTask;



public class ContentParser<T> extends Task<Void>{

    private ObjectsRepository<T> repo=null;
    private ObjectParser<T> objectParser=null;
    
    
    private long numberOfObjects=0;
    private long  currentObjectIndex=0;

    private volatile boolean isWorking=false;


    public boolean isAlive(){
        return isWorking;
    }

    
    public ContentParser(MainParsingTask<T> parentTask){
       isWorking=true; 
       this.repo=parentTask.getRepo();
       this.objectParser=parentTask.getParser();
      
    }

    

    
    public Void call(){
        start();
        isWorking=false;
        return null;

    }

    private void start(){
        SavedPage page=null;
        T object=null;
        
        
        numberOfObjects=repo.initContentCursor();
               
        page=repo.getNextContentItem();
        
        updateProgress(0, numberOfObjects);
        
        while (page!=null){
           
           
            if(isCancelled()){
                break;
            }
            
        
            if(!repo.isObjectParsed(page.url)){
                try{
                    object=objectParser.getObjectFromString(page.content,page.url);
                    repo.addObject(object);                    
                 } catch(ParseErrorException e){
                    System.out.println("ParseErrorException");
                     e.printStackTrace();
                 } catch(ObjectSavingException e){
                    System.out.println("ObjectSavingException");
                     e.printStackTrace();
                 } catch(Exception e){
                    System.out.println("oo..oooyyyy");
                 e.printStackTrace();

                }

            }

            page=repo.getNextContentItem();
            updateProgress(++currentObjectIndex, numberOfObjects);
        }

                   
                  
              
      


        
        
    }

  
}







