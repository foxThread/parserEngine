package parser.utils.partfinder;

import java.util.ArrayList;

import parser.utils.RegParser;
import parser.utils.HtmlParser;

public class AddressPartFinderImpl implements AddressPartFinder{

    private ArrayList<String> typeWritingVariants=new ArrayList<>();
    private HtmlParser htmlParser=new HtmlParser();


    private  String  getFirstBuildingNumberFromString(String s){
        
        RegParser parser=new RegParser();
        parser.parseString("(?U)(\\d+\\w*)", s);
        
        String intString=parser.getGroup(1);
        return intString;

    }


    
   

   

    public void addtypeWritingVariant(String variant){
        typeWritingVariants.add(variant);

    }

    private String getBasePartOfRegularExpression(){

        
        String  result;
        int numberOfVariants=typeWritingVariants.size();
        if(numberOfVariants==0){
            return null;
        }



        result="\\b"+typeWritingVariants.get(0)+"\\b";
        
        for(int i=1;i<numberOfVariants;i++){
            result=result+"|"+"\\b"+typeWritingVariants.get(i)+"\\b";
        }

               
        return result;



    }
    
    
    public AddressPart getAddressPart(String addressString){


        RegParser regParser=new RegParser();
     

        String basePart=getBasePartOfRegularExpression();
        
        String regExpression1="(?U),\\s*("+basePart+")"+"[\\.\\s]\\s*([\\w\\d\\-\\s\\.]+)\\s*,(.*)"; 

        String regExpression6="(?U)^\\s*("+basePart+")"+"[\\.\\s]\\s*([\\w\\d\\-\\s\\.]+)\\s*,(.*)"; 

        String regExpression7="(?U),\\s*("+basePart+")"+"[\\.\\s]\\s*([\\w\\d\\-\\s\\.]+)\\s*$"; 



        String regExpression2="(?U)([\\w\\d\\-\\s\\.]+)("+basePart+")"+
        "[\\.\\s]\\s*,(.*)"; 


        String regExpression3="(?U)([\\w\\d\\-\\s\\.]+)("+basePart+")"+
       "\\s*$"; 

        String regExpression5="(?U)([\\w\\d\\-\\s\\.]+)("+basePart+")"+
       "\\.\\s*$"; 


        String regExpression4="(?U)([\\w\\d\\-\\s\\.]+)("+basePart+")"+
       ",(.*)";   

       AddressPart addressPart=new AddressPart();
      
       
      
      
       boolean result=regParser.parseString(regExpression1,addressString);
      
       if(result){
        
          addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(1));
          addressPart.value=regParser.getGroup(2);
          addressPart.buildingNumber=getFirstBuildingNumberFromString(regParser.getGroup(3));
       
          return addressPart;

       }

       result=regParser.parseString(regExpression6,addressString);
       if(result){
        addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(1));
        addressPart.value=regParser.getGroup(2);
        addressPart.buildingNumber=getFirstBuildingNumberFromString(regParser.getGroup(3));
        return addressPart;

       }


       result=regParser.parseString(regExpression2,addressString);
       if(result){
        addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(2));
        addressPart.value=regParser.getGroup(1);
        addressPart.buildingNumber=getFirstBuildingNumberFromString(regParser.getGroup(3));
        return addressPart;
       }

       result=regParser.parseString(regExpression3,addressString);
       if(result){
        addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(2));
        addressPart.value=regParser.getGroup(1);
        return addressPart;
       }

       result=regParser.parseString(regExpression4,addressString);
       if(result){
        addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(2));
        addressPart.value=regParser.getGroup(1);
        addressPart.buildingNumber=getFirstBuildingNumberFromString(regParser.getGroup(3));
        return addressPart;
        
       }

       result=regParser.parseString(regExpression5,addressString);
       if(result){
        addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(2));
        addressPart.value=regParser.getGroup(1);
        return addressPart;

       }


       result=regParser.parseString(regExpression7,addressString);
      
       if(result){
        
          addressPart.type=htmlParser.getStreetTypeNormalized(regParser.getGroup(1));
          addressPart.value=regParser.getGroup(2);
                
          return addressPart;

       }
       
      
       return null;

      
    }



}

