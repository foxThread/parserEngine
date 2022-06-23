package parser.htmlgetters;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class SeleniumFireFoxHtmlGetter extends HtmlGetter {
    
    
    private  WebDriver driver=null;
 
    
    
    
    
    public SeleniumFireFoxHtmlGetter(String pathToDriver){

        System.setProperty("webdriver.gecko.driver",pathToDriver);
        driver=new FirefoxDriver();    
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
 

    }  
    
    
    
    
    protected String getHtmlString(String urlLink){
        
        driver.get(urlLink);
        String pageText=driver.getPageSource();
       
        return pageText;

    }

}
