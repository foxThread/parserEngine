package parser.typicaltasks.jfx.subtasks;


 
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.concurrent.Task;
import parser.exceptions.HtmlLoadException;
import parser.exceptions.ObjectSavingException;
import parser.htmlgetters.HtmlGetter;
import parser.interfaces.ObjectsRepository;
import parser.typicaltasks.jfx.MainParsingTask;
import parser.utils.Threads;



public class WebPagesLinksCollector<T> extends Task<Void> {

   
   
    
  
    private ObjectsRepository<T> repo=null;
    private HtmlGetter htmlGetter;


 
    private int firstPage=0;
    private int lastPage=0;
    private String baseUrl=null;
    private String baseUrlForPages=null;
    private String pagePrefix=null;

    private int  numberOfObjects=0;
    private int  currentObjectIndex=0;

    private volatile boolean isWorking=false;


    public boolean isAlive(){
        return isWorking;
    }

  


    public Void call(){
        start();
        isWorking=false;
        return null;

    }

    public void setParameters(int firstPage,int lastPage){
        this.firstPage=firstPage;
        this.lastPage=lastPage;
     
    }
       
    public WebPagesLinksCollector(MainParsingTask<T> parentTask){
        
        
        isWorking=true;
        this.repo=parentTask.getRepo();
        this.htmlGetter=parentTask.getHtmlGetter();
        this.baseUrl=parentTask.getBaseUrl();
        this.baseUrlForPages=parentTask.getBaseUrlForPages();
        this.pagePrefix=parentTask.getPagePrefix();
        
    }

  


    private void getLinksOnPage (String url) throws HtmlLoadException{
        
       
        Element rootElement=null;
        
        htmlGetter.getHtml(url);
        rootElement=htmlGetter.getRootElement();
        
        Elements links=rootElement.select("a[data-marker*=item-title]");
 
        for (Element link:links){

            String linkString=baseUrl+link.attr("href");
            if(!repo.isLinkExist(linkString))
            try{ 
                repo.addLink(linkString);
            } catch(ObjectSavingException e){
                e.printStackTrace();
            }    

        } 
        
       
    }


   public void start(){
    
        numberOfObjects=(lastPage-firstPage+1);
        currentObjectIndex=0;
        
        ArrayList <Integer> numbersOfPages=new ArrayList<Integer>();
    
    
        updateProgress(0, numberOfObjects);
        for (int i=firstPage;i<=lastPage;i++){
            numbersOfPages.add(i);
           
        }

        Collections.shuffle(numbersOfPages);
   
        for(int currentPageNumber:numbersOfPages){

            if(isCancelled()){
                break;
            }
           
            String urlOfPage=baseUrlForPages+pagePrefix+Integer.toString(currentPageNumber);
            try{
                getLinksOnPage(urlOfPage);
            } catch (HtmlLoadException e){
                continue;
            } 
            updateProgress(++currentObjectIndex, numberOfObjects);
            Threads.pauseThread(3,5);
           
        }
        
    }


}







    

