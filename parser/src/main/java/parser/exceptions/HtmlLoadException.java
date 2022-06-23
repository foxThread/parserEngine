package parser.exceptions;

public class HtmlLoadException extends Exception {

    private String urlLink;

    public String getUrlLink(){
        return urlLink;

    }

    public HtmlLoadException(String urlLink){
        super("Error loading URL: "+urlLink);
        this.urlLink=urlLink;

    }

    
}