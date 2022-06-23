package parser.htmlgetters.chromium;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;

import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefPdfPrintCallback;
import org.cef.callback.CefStringVisitor;

import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.misc.CefPdfPrintSettings;

import javax.swing.*;


import java.awt.*;
import java.io.File;




class ContentHandler implements CefStringVisitor{

    private Browser browser;
    public ContentHandler(Browser browser){
        this.browser=browser;
    }
    
    @Override
    public void visit(String content){
        browser.setContent(content);
        browser.setLoadingStatus(true);


    }

}




public class Browser {


    private String pathToPdf;
   
    private  CefApp     cefApp;
	private  CefClient  client;
	private  CefBrowser browser;
    private JFrame browserFrame;

   
  
    private volatile boolean loaded=false;
    private volatile boolean pdfPrinted=false;
    private String content;
    private ContentHandler contentHandler;

   
    private  final boolean OFFSCREEN = false;
    private  final boolean TRANSPARENT = false;

    
    
    public void setContent(String content){
        this.content=content;
    }


    public String getContent(){
        return content;
    }

    
    public boolean wasLoaded(){
        return loaded;

    }

    public void setLoadingStatus(boolean status){
        this.loaded=status;
    }


    
    
    
    public Browser(String pathToPdf){


        this.pathToPdf=pathToPdf;
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;; 
        cefApp=CefApp.getInstance(settings);
        client=cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());
        browserFrame=new JFrame();
        

       client.addLoadHandler(
            new CefLoadHandlerAdapter() {
              
                
                public void onLoadEnd(org.cef.browser.CefBrowser arg0, org.cef.browser.CefFrame arg1, int arg2) {
                    System.out.println("CEF.OnLoadEnd : Fired");
                   
                   
                
                }
                
                public void onLoadingStateChange(CefBrowser browser,boolean isLoading,boolean canGoBack,boolean canGoForward) {
                    
                    System.out.println("CEF.OnLoadingStateChange : Fired");
               
                  
                   if(! isLoading){

                        browser.getSource(contentHandler);
                        System.out.println("CEF.OnLoadingStateChange : Loading fully completed!!!!");
                  

                    }
                    
                  
                }

                public void onLoadError(org.cef.browser.CefBrowser arg0, org.cef.browser.CefFrame arg1, org.cef.handler.CefLoadHandler.ErrorCode arg2, java.lang.String arg3, java.lang.String arg4) {
                    System.out.println("CEF.OnLoadError : Fired");
                    System.out.println("Event: "+arg2.name());
                
                }
                
               


            
        });
        browser=client.createBrowser("", OFFSCREEN, TRANSPARENT);
       
       
        browserFrame.add(browser.getUIComponent(), BorderLayout.CENTER);
        browserFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        browserFrame.setSize(1200,900);
        browserFrame.setVisible(true);
        contentHandler=new ContentHandler(this);
        
        try{
            Thread.sleep(5000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }   
        


    }

    public String getHtml(String url){
            
            loaded=false;
            browser.loadURL(url); 
         
         
            while(!loaded){
    
            }
            
            System.out.println("Starting pdf printing...");
            
            pdfPrinted=false;
            
            String fullPath=getFullPathByUrl(url);

            if(fullPath!=null){
                browser.printToPDF(fullPath, new CefPdfPrintSettings(),new CefPdfPrintCallback() {
                    @Override
                    public void onPdfPrintFinished(String path,boolean ok){
                        pdfPrinted=true;

                    }
                });

                while(!pdfPrinted){
    
                }

                System.out.println("printing pdf succesful...");
            }    else{
                System.out.println("File already printed or saving Error");
            } 
        
           
            return content;
    
            
       

        
    }


    
    private String getPdfFileNameByUrl(String urlLink){
        
        RegParser reg=new RegParser();

        reg.parseString("/([^/]+)$",urlLink);
        return reg.getGroup(1);
    }

    
    private boolean isFileExist(String fullPath){
        File f=new File(fullPath);
        return f.exists();
 
    }
    
    
    
    private String getFullPathByUrl(String url){
        
        String pdfFileName=getPdfFileNameByUrl(url);

        if(pdfFileName==null){
            return null;
        }

        if( isFileExist(pathToPdf+"\\"+pdfFileName+".pdf")){
            return null;
        }
        
        return pathToPdf+"\\"+pdfFileName+".pdf";

    }



    
}
