package parser.htmlgetters.chromium;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegParser{

    private Matcher matcher=null;
    
   
    public boolean parseString(String reg,String str){
       
        Pattern r=Pattern.compile(reg);
        matcher=r.matcher(str);
       
        if (matcher.find()){
            return true;
        } else{
            return false;
        }
    }

    public String getGroup(int groupNumber){
    
        try{
         return matcher.group(groupNumber);
         } catch(Exception e){
             return null;
         }



    }
}


    