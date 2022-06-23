package parser.config.entities;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;





public class TaskConfig {


    public TaskConfig(String pathToFile){
        FileInputStream is;
        Properties config=new Properties();

        try{
            is=new FileInputStream(new File(pathToFile));
         
            config.load(new InputStreamReader(is,"UTF-8"));
            
            firstPage=Integer.parseInt(config.getProperty("firstPage"));
            lastPage=Integer.parseInt(config.getProperty("lastPage"));
            dbName=config.getProperty("dbName");
            taskName=config.getProperty("taskName");
            baseUrl=config.getProperty("baseUrl");
            baseUrlForPages=config.getProperty("baseUrlForPages");
            pagePrefix=config.getProperty("pagePrefix");
            cityName=config.getProperty("cityName");
            pathToExcel=config.getProperty("pathToExcel");
            pdfSavingPath=config.getProperty("pdfSavingPath");

      
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }

   

   
    public TaskConfig() {
    }




    public int firstPage;

   
    public int lastPage;
    
 
    public String dbName;
    
   
    public String taskName;
    
  
    public String baseUrl;
    
   
    public String baseUrlForPages;
    
    
    
    public String pagePrefix;

    public String cityName;

    public String pathToExcel;

    public String pdfSavingPath;

}
