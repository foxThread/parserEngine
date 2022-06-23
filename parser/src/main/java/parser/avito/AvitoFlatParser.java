package parser.avito;







import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parser.abstractparsers.flatparser.entities.*;


import parser.utils.AddressParser;
import parser.utils.RegParser;



import parser.utils.HtmlParser;

import parser.abstractparsers.flatparser.FlatParser;




public class AvitoFlatParser extends FlatParser {

    private HtmlParser utils=new HtmlParser();


   
    protected  boolean isValid(){
       
        
        try{
            rootElement.selectFirst("span[class*=item-address__string]").text();
            return true;
        } catch(Exception e){
            return false;
        }

    }


    protected  FlatType getFlatType(){
        
        String titleText=getTitleText();

        RegParser reg=new RegParser();

        if (reg.parseString("(?U)\\bстудия\\b",titleText)){
            return(FlatType.STUDIO);   
        } 
              
                
        if (reg.parseString("(?U)\\bКомната\\b",titleText)){
            return(FlatType.ROOM);
        } 
        
        return(FlatType.FLAT);
        
    }

    protected  String getBulletinText(){
        try{
        Element desc=rootElement.selectFirst("div[class*=item-description-title]").nextElementSibling();
        return utils.getText(desc);
        } catch(NullPointerException e){
            return null;
        }
    
    }


    protected  String getTownArea(){

        Element area=rootElement.selectFirst("span[class*=item-address-georeferences]");
        String areaText=utils.getValueFromStringByRegularExpression("(?U)р-н\\s(\\w+)",utils.getText(area),1); 
        return areaText;

    }


    protected  Address getAddress(){
        AddressParser addressParser=new AddressParser(getAddressText());
        Address address=addressParser.getAddress();
        
        address.streetName=utils.deleteSpacesAroundString(address.streetName);
        address.settlementName=utils.deleteSpacesAroundString(address.settlementName);
        return address;
    }


    protected  Float  getSquare(){

        RegParser reg=new RegParser();
        String titleText=getTitleText();

        reg.parseString("(\\d+)[\\.,]*(\\d*)\\s*м",titleText);
        try{
            return utils.makeFloat(reg.getGroup(1)+"."+reg.getGroup(2));
        } catch (Exception e){
            return null; 
        }

    }

    protected  String  getPrice(){
        Element price=rootElement.selectFirst("span[class*=js-item-price]");
        String priceText=utils.getText(price); 
        return priceText;

    }

    protected  String getWallMaterial(){
        try{
            Element span=rootElement.selectFirst("span[class*=item-params-label]:containsOwn(Тип дома:)");
            Element li=span.parent();
            return li.ownText();
            } catch(Exception e){
                return null;
            }



    }


    protected  Integer getBuildingYear(){
        try{
            Element span=rootElement.selectFirst("span[class*=item-params-label]:containsOwn(Год постройки:)");
            Element li=span.parent();
            return utils.makeInt(li.ownText());
            } catch (Exception e){
                return null;
            }
    

    }


    private String  getParameter(String parameterName){

        RegParser reg=new RegParser();

        Elements lis=rootElement.select("li[class*=params-paramsList__item]");
        for (Element li :lis){
            Element span=li.selectFirst("span");
            String spanText=utils.getText(span);

            if (reg.parseString(parameterName, spanText)){
                return utils.getOwnText(li);
            }


        }

        return null;


    }
    
    
    protected  String getRepairType(){
       /* try{
            Element span=rootElement.selectFirst("span[class*=item-params-label]:containsOwn(Ремонт:)");
            Element li=span.parent();
            return li.ownText();
            } catch(Exception e){
                return null;
            }*/
        return getParameter("Ремонт");    

    }

    protected  Integer getRoomsAmount(){
        String titleText=getTitleText();

        RegParser reg=new RegParser();

        if (reg.parseString("(?U)\\bстудия\\b",titleText)){
            return(1);
        } 

        reg.parseString("(\\d+)-к", titleText);
          
            try{
                return Integer.parseInt(reg.getGroup(1));
            } catch (Exception e){
                return(null);
            }
   
    }


    protected  Integer getNumberOfFloors(){
        String titleText=getTitleText();
        RegParser reg=new RegParser();

        reg.parseString("(\\d+)/(\\d+)\\s*эт",titleText);

        try{
           return utils.makeInt(reg.getGroup(2));
        } catch (Exception e){
           return null;
        }


    }


    protected  Integer getFloorNumber(){
        String titleText=getTitleText();
        RegParser reg=new RegParser();
        reg.parseString("(\\d+)/(\\d+)\\s*эт",titleText);

        try{
           return utils.makeInt(reg.getGroup(1));
        } catch (Exception e ){
            return null;
        }

    }


    protected  String getDateOfPlacing(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");   
        String dateStr=null; 
        LocalDate placingDate=null;


        Element dateOfPlacingStr=rootElement.selectFirst("span[class*=style-item-metadata-date]");
        String dateString = utils.getText(dateOfPlacingStr);

        String dateOfParsing=getDateOfParsing();

       
    
        LocalDate parseDate=LocalDate.parse(dateOfParsing,formatter);
       

       if( utils.getValueFromStringByRegularExpression("(?U)(\\bвчера\\b)",dateString,1)!=null){

           placingDate=parseDate.minus(Period.ofDays(1)); 
           dateStr=placingDate.format(formatter);      
          

          } else

       if( utils.getValueFromStringByRegularExpression("(?U)(\\bсегодня\\b)",dateString,1)!=null){
            
            dateStr=dateOfParsing;

          } else {
            try{  
            dateStr=getDateFromString(dateString,parseDate.getYear());
            } catch (NullPointerException e){
                dateStr="";
            }            

          }


        return dateStr;


    }



    protected  String getInfoSource(){
        return "www.avito.ru";

    }


    protected  String getNumberOfSource(){
        Element numberOfSource=rootElement.selectFirst("span[data-marker*=item-view/item-id]");
        return utils.getValueFromStringByRegularExpression("(?U)(\\d+)", utils.getText(numberOfSource), 1);
        
    }


    protected  String getAgency(){
        Element agencyNameA=rootElement.selectFirst("div[class*=seller-info-name]>a");
        Element agencyTypeDiv=rootElement.selectFirst("div[data-marker^=seller-info/label]");

        String agencyType=utils.getText(agencyTypeDiv);
        String agencyName=utils.getText(agencyNameA);

        if (agencyName==null){
            return agencyType;
        }
     
        return agencyType+", "+agencyName;
   

    }


    


    public String getDateFromString(String dateString,int year){

  
        
        String[] months = {
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"};

        String[] monthsIndexes={"01", "02", "03", "04", "05", "06",
        "07", "08", "09", "10", "11", "12"
        };    
        RegParser regParser=new RegParser();
       
        
        if(regParser.parseString("(?U)^\\s*(\\d+?)\\s+?(\\w+)", dateString)){
            
            String day=regParser.getGroup(1);
            if (day.length()==1){
                day="0"+day;


            }
            String month=regParser.getGroup(2);

           
           
              
            int index=0;
            int curIndex=-1;
            for (String curMonth:months){
             
                if (curMonth.equals(month)){
                    
                    curIndex=index;
                }
                index++;
            }
            
            if(curIndex != (-1)){
                
                try{
                return day+"."+monthsIndexes[curIndex]+"."+Integer.toString(year);
                } catch(NullPointerException e){
                    return "";
                }
            }
               
        }

        
        return "";

   } 
   
  
  
    

    protected String  getDateOfParsing() {
        Date currentDate=new Date();
        DateFormat dateFormat =new SimpleDateFormat("dd.MM.yyyy");
        String dateStr=dateFormat.format(currentDate);
       
        return dateStr;


    }


    protected GeoPosition getGeoPosition(){

        GeoPosition geoPosition=new GeoPosition();

        Element geo=rootElement.selectFirst("div[class*=b-search-map]");
      
        try{
            geoPosition.lat=geo.attr("data-map-lat");
            geoPosition.lon=geo.attr("data-map-lon");
            return geoPosition;
        } catch (NullPointerException e){
            return null;
        }
        
       
    }    

       

   



    


    

   


    

   
   

    private String getAddressText(){
        Element address=rootElement.selectFirst("span[class*=item-address__string]");
        
        return utils.getText(address);
        
    }

    private String getTitleText(){
        Element title=rootElement.selectFirst("span[class*=title-info-title-text]");
        return utils.getText(title);

    }
    


    
   
    
    
    
    

    


    

    


   




    
}
