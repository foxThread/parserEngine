package parser.typicalrepos;

import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;



import parser.entities.SavedPage;


import parser.exceptions.ObjectSavingException;



import parser.interfaces.ObjectsRepository;



public class MongoRepository<T> implements ObjectsRepository<T>{
    
   
    private MongoDataSource ds=null;
    private MongoDatabase db=null;
  

    private  MongoCollection<SavedPage> linksCollection=null;
    private  MongoCollection<SavedPage> contentCollection=null;
    private  MongoCollection<T> objectsCollection=null;

    private  MongoDatabaseSaver<SavedPage> linksSaver=null;
    private  MongoDatabaseSaver<SavedPage> contentsSaver=null;
    private  MongoDatabaseSaver<T> objectSaver=null;
  
    MongoCursor<SavedPage> cursor=null;



    public MongoRepository(String dbName,String taskName,Class<T> classType){
                
        ds=MongoDataSource.getInstance();
        db=ds.getDatabase(dbName);
       
       
        String linksCollectionName=taskName+"-links";
        String flatsContentCollectionName=taskName+"-content";
        String flatsCollectionName= taskName+"-objects";
        
        linksCollection=db.getCollection(linksCollectionName,SavedPage.class);
        contentCollection=db.getCollection(flatsContentCollectionName,SavedPage.class);
        objectsCollection=db.getCollection(flatsCollectionName,classType);
        
        linksSaver=new MongoDatabaseSaver<SavedPage>(dbName,linksCollectionName);
        contentsSaver=new MongoDatabaseSaver<SavedPage>(dbName,flatsContentCollectionName);
        objectSaver=new MongoDatabaseSaver<>(dbName,flatsCollectionName);

       
    }


   

  public void addLink(String url) throws ObjectSavingException{
      
     SavedPage page=new SavedPage();
     page.url=url;   
     linksSaver.save(page);
     

  } 



  public void addContent(SavedPage savedPage) throws ObjectSavingException{
         
     contentsSaver.save(savedPage);
      
  }

    
  
  public void addObject(T object) throws ObjectSavingException{
    
    objectSaver.save(object);
      
  }
   
    
   public  ArrayList<SavedPage> getAllLinks(){
             
        ArrayList <SavedPage> links=new ArrayList<SavedPage>();
        linksCollection.find().into(links);
        return links;


   }    


   public  ArrayList<T> getAllObjects(){
             
    ArrayList <T> flats=new ArrayList<T>();
    objectsCollection.find().into(flats);
    return flats;


}    



public boolean isLinkExist(String url){
    ArrayList <SavedPage> contents=new ArrayList<SavedPage>();
    linksCollection.find(new Document("url",url)).into(contents);
    
    if(contents.size()==0){
        return false;
    }

    return true;

}
   public boolean isContentLoaded(String url){
       
       ArrayList <SavedPage> contents=new ArrayList<SavedPage>();
       contentCollection.find(new Document("url",url)).into(contents);
       
       if(contents.size()==0){
           return false;
       }

       return true;


   }


   
   
   
   public boolean isObjectParsed(String url){
        ArrayList <T> flats=new ArrayList<T>();
        objectsCollection.find(new Document("urlLink",url)).into(flats);
    
        if(flats.size()==0){
            return false;
    }

    return true;


   }


  public long getObjectsAmount(){
      return objectsCollection.countDocuments();
  } 

  public long initContentCursor(){
      cursor=contentCollection.find().cursor();
      return contentCollection.countDocuments();

  }
  
  public SavedPage getNextContentItem(){
      if(cursor.hasNext()){
          return cursor.next();
      }
      cursor.close();
      return null;


  }



}
