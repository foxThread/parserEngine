package parser.utils;





import parser.abstractparsers.flatparser.entities.*;

import parser.utils.partfinder.AddressPartFinder;
import parser.utils.partfinder.AddressPartFinderImpl;
import parser.utils.partfinder.AddressPart;





public class AddressParser {

    private String addressString;
   

    public AddressParser(String addressString){
        this.addressString=addressString;
       
        
    }



   
   public  AddressPartFinder getSettlementFinder(){

    AddressPartFinder settlementFinder=new AddressPartFinderImpl();
    
    settlementFinder.addtypeWritingVariant("поселок");
    settlementFinder.addtypeWritingVariant("пос");
    settlementFinder.addtypeWritingVariant("п");
    settlementFinder.addtypeWritingVariant("посёлок");

    settlementFinder.addtypeWritingVariant("микрорайон");
    settlementFinder.addtypeWritingVariant("мкрн");
    settlementFinder.addtypeWritingVariant("мкр");
    settlementFinder.addtypeWritingVariant("м-рн");
    settlementFinder.addtypeWritingVariant("мрн");
    settlementFinder.addtypeWritingVariant("село");
    settlementFinder.addtypeWritingVariant("с");

    return settlementFinder;


   }

   private AddressPartFinder getStreetFinder(){
    
    AddressPartFinder streetFinder=new AddressPartFinderImpl();

    streetFinder.addtypeWritingVariant("пер");
    streetFinder.addtypeWritingVariant("переулок");
    streetFinder.addtypeWritingVariant("пр-т");
    streetFinder.addtypeWritingVariant("пр-кт");
    streetFinder.addtypeWritingVariant("просп");
    streetFinder.addtypeWritingVariant("проспект");
    streetFinder.addtypeWritingVariant("пр");
    streetFinder.addtypeWritingVariant("проезд");
    streetFinder.addtypeWritingVariant("пл");
    streetFinder.addtypeWritingVariant("площадь");
    streetFinder.addtypeWritingVariant("ул");
    streetFinder.addtypeWritingVariant("улица");
    streetFinder.addtypeWritingVariant("жилой\\sкомплекс");
    streetFinder.addtypeWritingVariant("шоссе");
    streetFinder.addtypeWritingVariant("ш");
    streetFinder.addtypeWritingVariant("бульв");
    streetFinder.addtypeWritingVariant("б-р");
    streetFinder.addtypeWritingVariant("бульвар");
    streetFinder.addtypeWritingVariant("квартал");
    streetFinder.addtypeWritingVariant("кв-л");
    streetFinder.addtypeWritingVariant("кв");

    return streetFinder;


}

    
    
    public Address getAddress(){
           
        Address address=new Address();
       
      
        AddressPartFinder settlementFinder=getSettlementFinder();
        AddressPartFinder streetFinder=getStreetFinder();
       
   
        AddressPart settlementPart=settlementFinder.getAddressPart(addressString);
        AddressPart streetPart=streetFinder.getAddressPart(addressString);
      
        
        if(settlementPart==null && streetPart==null){
            address.streetName=addressString;
            return address;
        }
        
        if(settlementPart!=null){
           address.settlementType=settlementPart.type;
           address.settlementName=settlementPart.value;
       } 
        
       if(streetPart==null){
           address.streetName=address.settlementName;
           address.streetType=address.settlementType;
           address.buildingNumber=settlementPart.buildingNumber;
       } else{
           address.streetName=streetPart.value;
           address.streetType=streetPart.type;
           address.buildingNumber=streetPart.buildingNumber;

       }


       return address;
       


    }


    public static void main(String [] argc){
     
        AddressParser parser=new AddressParser("Хабаровский край, Хабаровск, ул. А.А. Вахова, 8");
     // AddressParser parser=new AddressParser("Хабаровский край, Хабаровский р-н,  c. Гаровка-2 , 19");
      Address address= parser.getAddress();
      System.out.println(address.settlementType);
      System.out.println(address.settlementName);
      System.out.println(address.streetType);
      System.out.println(address.streetName);
      System.out.println(address.buildingNumber);




   /*   String regExpression1="(?U),\\s*(\\bпоселок\\b|\\bпос\\b|\\bп\\b|\\bпосёлок\\b|\\bмикрорайон\\b|\\bмкрн\\b|\\bмкр\\b|\\bм-рн\\b|\\bмрн\\b|\\bсело\\b|\\bс\\b)[\\.\\s]\\s*([\\w\\d\\-\\s]+)\\s*,";
      String addressString="Хабаровский край, Хабаровский р-н, с. Гаровка-2, 19";
      RegParser regParser=new RegParser();

      boolean result=regParser.parseString(regExpression1,addressString);
      System.out.println(result);
     */ 
 
 /*AddressPartFinder partFinder=parser.getStreetFinder();
 AddressPart ap=partFinder.getAddressPart("Хабаровский край, Хабаровский р-н, с. Гаровка-2 , 19");
 System.out.println(ap.type);*/



}

    
}
