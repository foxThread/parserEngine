package parser.abstractparsers.flatparser;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import parser.abstractparsers.flatparser.entities.Address;
import parser.abstractparsers.flatparser.entities.Flat;
import parser.abstractparsers.flatparser.entities.GeoPosition;

import parser.abstractparsers.flatparser.entities.FlatType;
import parser.exceptions.ParseErrorException;
import parser.interfaces.ObjectParser;


public abstract class FlatParser implements  ObjectParser<Flat>{

    protected String content;
    protected String url;
    protected Element rootElement=null;
    
    
   
    
    
    public Flat getObjectFromString(String content,String url) throws ParseErrorException{


        this.content=content;
        this.url=url;

        if (content==null){
            throw new ParseErrorException();
        }
        
        try{
            this.rootElement=Jsoup.parse(content);
        } catch(Exception e){
            throw new ParseErrorException();
        }

        if (!isValid()){  
            System.out.println("not valid format");      
            throw new ParseErrorException(); 
        }
        
        
        Flat flat=new Flat();

        try{
            flat.type=getFlatType();
            flat.bulletinText= getBulletinText();
            flat.townArea= getTownArea();
            flat.address=getAddress();
            flat.square= getSquare();
            flat.price= getPrice();
            flat.wallMaterial= getWallMaterial();
            flat.buildingYear= getBuildingYear();
            flat.repairType= getRepairType();
            flat.roomsAmount= getRoomsAmount();
            flat.numberOfFloors= getNumberOfFloors();
            flat.floorNumber= getFloorNumber();
            flat.dateOfPlacing= getDateOfPlacing();
            flat.infoSource= getInfoSource();
            flat.numberOfSource= getNumberOfSource();
            flat.agency= getAgency();
            flat.urlLink=url;
            flat.dateOfParsing=getDateOfParsing();
            flat.geoPosition=getGeoPosition();
         
        
        } catch(Exception e){
            System.out.println("Error parsing url:\n "+url);
            e.printStackTrace();
        }

        return flat;
    }





    protected abstract boolean isValid();
    protected abstract FlatType getFlatType();
    protected abstract String getBulletinText();
    protected abstract String getTownArea();
    protected abstract Address getAddress();
    protected abstract Float  getSquare();
    protected abstract String  getPrice();
    protected abstract String getWallMaterial();
    protected abstract Integer getBuildingYear();
    protected abstract String getRepairType();
    protected abstract Integer getRoomsAmount();
    protected abstract Integer getNumberOfFloors();
    protected abstract Integer getFloorNumber();
    protected abstract String getDateOfPlacing();
    protected abstract String getInfoSource();
    protected abstract String getNumberOfSource();
    protected abstract String getAgency();
    protected abstract String getDateOfParsing();
    protected abstract GeoPosition getGeoPosition();


    



    
}
