package parser.utils;
import org.jsoup.nodes.Element;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;











public  class HtmlParser {
    
    
    public  String getStreetTypeNormalized(String streetType){
          
        String streetTypeNormalized=streetType;
       
        if( getValueFromStringByRegularExpression("(?U)(\\bпл\\b|\\bплощадь\\b)",streetType,1)!=null){
            streetTypeNormalized="площадь";
    
    
        }   
    
       


    if( getValueFromStringByRegularExpression("(?U)(\\bпер\\b|\\bпереулок\\b)",streetType,1)!=null){
        streetTypeNormalized="пер";


    }   

    if( getValueFromStringByRegularExpression("(?U)(\\bул\\b|\\bулица\\b)",streetType,1)!=null){
         streetTypeNormalized="улица";

    }   

    if( getValueFromStringByRegularExpression("(?U)(\\bпроезд\\b|\\bпр\\b)",streetType,1)!=null){
        streetTypeNormalized="пр";
    }




    if( getValueFromStringByRegularExpression("(?U)(\\bпр-т\\b|\\bпр-кт\\b|\\bпросп\\b|\\bпроспект\\b)",streetType,1)!=null){
        streetTypeNormalized="пр-т";

    }   


    if( getValueFromStringByRegularExpression("(?U)(\\bшоссе\\b|\\bш\\b)",streetType,1)!=null){
        streetTypeNormalized="ш";

    }
    
    if( getValueFromStringByRegularExpression("(?U)(\\bбульв\\b|\\bб-р\\b|\\bбульвар\\b)",streetType,1)!=null){
        streetTypeNormalized="бульвар";

    }   

    if( getValueFromStringByRegularExpression("(?U)(\\bквартал\\b|\\bкв-л\\b|\\bкв\\b)",streetType,1)!=null){
        streetTypeNormalized="квартал";


    }
    
  
    
    if( getValueFromStringByRegularExpression("(?U)(\\bпоселок\\b|\\bпос\\b|\\bп\\b|\\bпосёлок\\b)",streetType,1)!=null){
            streetTypeNormalized="поселок";
    }

    if( getValueFromStringByRegularExpression("(?U)(\\bмикрорайон\\b|\\bмкрн\\b|\\bмкр\\b|\\bм-рн\\b|\\bмрн\\b)",streetType,1)!=null){
                streetTypeNormalized="микрорайон";
    }            
    
    if( getValueFromStringByRegularExpression("(?U)(\\bсело\\b|\\bс\\b)",streetType,1)!=null){
                    streetTypeNormalized="село";

    
      

    }  
    
    
 
    
    return  streetTypeNormalized;
    
    

}
    


   
    public String deleteSpacesAroundString(String str){
        String resultStr=getValueFromStringByRegularExpression("(?U)^\\s*(.+?)\\s*?$", str, 1);
        return resultStr;
 
 
    }
    

    public boolean isNumber(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }


    public String  getFirstBuildingNumberFromString(String s){
        String intString=getValueFromStringByRegularExpression("(?U)(\\d+\\w*)", s, 1);
        return intString;


    }
    
    public Integer makeInt(String s){
        try {
            return Integer.parseInt(s);

        } catch (Exception e){
            return null;
        }
    }

    public Float makeFloat(String s){
        try {
            return Float.parseFloat(s);

        } catch (Exception e){
            return null;
        }
    }

    
    public String getText(Element element){
        try{
            return element.text();
        } catch (Exception e){
            return null;
        }

    }

    public String getOwnText(Element element){
        try{
            return element.ownText();
        } catch (Exception e){
            return null;
        }

    }
    
    
    
    public boolean isStringBeginWithDigit(String s){
        String regExpression="(^\\s*\\d+)";
        if (getValueFromStringByRegularExpression(regExpression,s,0)==null){
            return false;
        }
        return true;
        

    }
    //возвращает первую группу из переданной регулярки
    public String getValueFromStringByRegularExpression(String regularExpression,String str,int groupIndex){
        
        Pattern r=Pattern.compile(regularExpression);
        try{
             Matcher m=r.matcher(str);
        
       
            if (m.find()){
                return m.group(groupIndex);
            } else{
                return null;
            }

       } catch(NullPointerException e){
           return null;
       }
  
    }

     // получает элемент div class=value в зависимости от от содержания div class=label
    public Element getValueElementByLabelText(Element element,String labelText){
        
        Element valueDiv=element.selectFirst(
            "div.label:containsOwn"+"("+labelText+")"+ " + div.value");
        return(valueDiv);    
       

    }

    public float getFloatFromTwoStrings(String p1,String p2){
        String resultString=p1+"."+p2;
        return Float.parseFloat(resultString); 
    }

    public String decodeFromURL(String value) {
       
        try {
            String result = java.net.URLDecoder.decode(value, StandardCharsets.UTF_8.name());
            return result;
        } catch (UnsupportedEncodingException e) {
            return null;
          }
        
  

    }
    
    
   

}
