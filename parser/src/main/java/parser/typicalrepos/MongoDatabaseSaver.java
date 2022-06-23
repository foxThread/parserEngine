package parser.typicalrepos;


import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;


import parser.interfaces.ObjectSaver;

import parser.exceptions.ObjectSavingException;


public class MongoDatabaseSaver<T> implements ObjectSaver<T>{

    private MongoDataSource ds=null;
    private MongoCollection mongoCollection=null;
    
    
    
    public MongoDatabaseSaver(String dbName,String collectionName){
        
        ds=MongoDataSource.getInstance();
        mongoCollection=ds.getDatabase(dbName).getCollection(collectionName);

    }
    
    public  void save(T entity) throws ObjectSavingException {
        

        Gson gson=new Gson();
       
        org.bson.Document doc=org.bson.Document.parse(gson.toJson(entity));
        ds.insertDocument(doc,mongoCollection);
        System.out.println("Saved");             
    } 


    
}
