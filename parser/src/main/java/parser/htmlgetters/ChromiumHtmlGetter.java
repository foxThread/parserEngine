package parser.htmlgetters;

import parser.config.AppConfig;
import parser.htmlgetters.chromium.Browser;






public class ChromiumHtmlGetter extends HtmlGetter{

    private Browser browser;

    public ChromiumHtmlGetter(String pathToPdf){
       browser=new Browser(pathToPdf);

    }

    public String getHtmlString(String urlLink){
        
        return browser.getHtml(urlLink);
       

    
    
    }

   
           


}
