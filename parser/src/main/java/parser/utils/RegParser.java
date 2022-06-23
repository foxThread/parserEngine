package parser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegParser{

    private Matcher matcher=null;
    
   
    public boolean parseString(String reg,String str){
       
        Pattern r=Pattern.compile(reg);
        try{
            matcher=r.matcher(str);
        } catch (Exception e){
            matcher=null;
            return false;
        }   
       
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


    