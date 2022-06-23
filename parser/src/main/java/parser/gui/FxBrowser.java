package parser.gui;





import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

import javafx.scene.layout.Region;
import java.io.*;


import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;







public class FxBrowser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    private volatile String content=null;
    private volatile boolean isLoadingFinished=false;
    private static FxBrowser fxBrowserSingleton=null;
   
    
    public boolean isContentLoaded(){
        return isLoadingFinished;
    }

    public void setContentLoaded(boolean status){
        isLoadingFinished=status;
    }
    
    
    public static FxBrowser getInstance(){
        if(fxBrowserSingleton == null){
            fxBrowserSingleton=new FxBrowser();
        }
        return fxBrowserSingleton;
    }   
    
    
    private FxBrowser() {

       

        getStyleClass().add("browser");
        getChildren().add(browser);
        webEngine.getLoadWorker().stateProperty()
        .addListener((obs, oldValue, newValue) -> {
          if (newValue == State.SUCCEEDED) {
            org.w3c.dom.Document   xmlDom  = webEngine.getDocument();
            content=getStringFromDocument(xmlDom);
            System.out.println("Content is mine");
            isLoadingFinished=true;
        
            }
        });  
        
     
    }
   
    
   

    public void get(String url){
        
        content=null;
       
        webEngine.load(url);
    }
    
    
    public  String getContent(){
        return content;
    }
    
    private  String getStringFromDocument(org.w3c.dom.Document doc)
    {
        try
        {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
    catch(Exception ex)
       {
            ex.printStackTrace();
            return null;
       }
   } 
    
  
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}