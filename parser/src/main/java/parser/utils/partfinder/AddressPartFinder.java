package parser.utils.partfinder;

public interface AddressPartFinder{
    public void addtypeWritingVariant(String variant);
    public AddressPart getAddressPart(String addressString); 

}