package parser.config;




import parser.htmlgetters.ChromiumHtmlGetter;
import parser.htmlgetters.HtmlGetter;

import parser.interfaces.ObjectParser;
import parser.interfaces.ObjectsRepository;
import parser.typicalrepos.MongoRepository;

import parser.typicaltasks.jfx.MainParsingTask;
import parser.abstractparsers.flatparser.entities.Flat;
import parser.avito.AvitoFlatParser;
import parser.config.entities.TaskConfig;

public class AppConfig{
    
    public  static final String chromePath="\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\"";
    public static final  String seleniumChromeDriverPath="E:\\tools\\chromedriver.exe";
    public static final  String seleniumFirefoxDriverPath="E:\\tools\\geckodriver.exe";
    public static final  String pdfSavingPath="f:\\parser\\pdf";

    

    private static MainParsingTask <Flat> task=null;



    public static MainParsingTask <Flat> getTask(TaskConfig taskConfig){
                   
        ObjectParser<Flat> parser=new AvitoFlatParser();
        ObjectsRepository<Flat> repo=new MongoRepository<Flat>(taskConfig.dbName,taskConfig.taskName,Flat.class);
      
        HtmlGetter htmlGetter=new ChromiumHtmlGetter(taskConfig.pdfSavingPath);

        task=new MainParsingTask<Flat>(parser,repo,htmlGetter);
      
        
        task.setFirstPage(taskConfig.firstPage);

        task.setLastPage(taskConfig.lastPage);
        task.setBaseUrl(taskConfig.baseUrl);
        task.setBaseUrlForPages(taskConfig.baseUrlForPages);  
        task.setPagePrefix(taskConfig.pagePrefix);
        
        return task;
        
       
        
   }


  




}