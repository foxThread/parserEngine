package parser.exceptions;

public class DBConnectException extends Exception {
  
    public DBConnectException(String db){
        super("Error connecting to "+db);
         
    
    }
   
        
}
    

