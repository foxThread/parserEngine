package parser.htmlgetters;



import parser.gui.FxBrowser;

import javafx.application.Platform;



public class JavaFxHtmlGetter extends HtmlGetter{

    private FxBrowser browser;

    public JavaFxHtmlGetter(FxBrowser browser){
        this.browser=browser;

    }

    public String getHtmlString(String urlLink){
        
        browser.setContentLoaded(false);
     
        Platform.runLater( new Runnable(){
            @Override
            public void run(){
                browser.get(urlLink);
            }

        });


       long start =System.currentTimeMillis();
       while(!browser.isContentLoaded()){
          
           if( (System.currentTimeMillis()-start)>70000 ){
              System.out.println("Time for loading page was OUT");
              return null;
           }

           
       }
       
        
        System.out.println("We get content");
        String content=browser.getContent();
     
        return content;

       
    
    
    
    }

   
           


}
