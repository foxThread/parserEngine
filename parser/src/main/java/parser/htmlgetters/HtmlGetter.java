package parser.htmlgetters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import parser.exceptions.HtmlLoadException;





public abstract class HtmlGetter {
    
    
    private Element rootElement=null;
    protected abstract String getHtmlString(String urlLink);
    
    public   String getHtml(String urlLink) throws HtmlLoadException{
        
              
        String htmlString=getHtmlString(urlLink);
        if (htmlString==null){
            System.out.println("html is null");
            throw new HtmlLoadException(urlLink);

        }
        try{
            rootElement=Jsoup.parse(htmlString);
            return htmlString;
          } catch(Exception e){
              throw new HtmlLoadException(urlLink);
          }
    }
        
    
    public Element getRootElement() throws HtmlLoadException{
        
        if (rootElement !=null){
            return rootElement;
        }else{
            throw new HtmlLoadException("Html was not loaded");
        }

    }



}
