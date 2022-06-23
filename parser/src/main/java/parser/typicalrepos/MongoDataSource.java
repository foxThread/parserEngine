package parser.typicalrepos;




import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;


import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;






import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;






public class MongoDataSource {
    
    private static MongoDataSource singleton=null;
    private static MongoClient  mongoClient=null;

    private MongoDatabase db=null;
    
   
    
    private  static void init(){

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), 
        pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .codecRegistry(codecRegistry)
                                                                .build();

                        
        mongoClient=MongoClients.create(clientSettings);
                       
        }
        
    
    
    
    public static MongoDataSource getInstance(){
        if(singleton==null){
            singleton=new MongoDataSource();
            return singleton;
        }

        return singleton;


    }





    private MongoDataSource(){
        init();

    }
    
    
    public  void closeInstance(){
        mongoClient.close();
        singleton=null;
     }


     public MongoDatabase getDatabase(String dbName){
        return (mongoClient.getDatabase(dbName));

     }

     public MongoCollection getCollection(MongoDatabase db,String collectionName){
         return db.getCollection(collectionName);

     }
     
     
    public void insertDocument(org.bson.Document doc,MongoCollection col){
         col.insertOne(doc);



    } 
    

    
    
    
    
    

   




   

    


    



    }


    

