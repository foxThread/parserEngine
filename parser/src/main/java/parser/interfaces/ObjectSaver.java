package parser.interfaces;

import parser.exceptions.ObjectSavingException;

public interface ObjectSaver<T> {
    public void save(T object) throws ObjectSavingException; 
    
}
