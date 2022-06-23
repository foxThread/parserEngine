package parser.htmlgetters;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;





public class SeleniumChromeHtmlGetter extends HtmlGetter {
    
    private  WebDriver driver=null;
   

    
    public SeleniumChromeHtmlGetter(String pathToDriver) {                  
    
        System.setProperty("webdriver.chrome.driver",pathToDriver);
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
    } 

        
    protected String getHtmlString(String urlLink){
        
        driver.get(urlLink);
        String pageText=driver.getPageSource();
        return pageText;

    }


}
