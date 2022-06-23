package parser.interfaces;

import java.util.ArrayList;

import parser.entities.SavedPage;
import parser.exceptions.ObjectSavingException;

public interface ObjectsRepository<T> {

    public void addLink(String url) throws ObjectSavingException;
    public void addContent(SavedPage savedPage) throws ObjectSavingException;
    public void addObject(T object) throws ObjectSavingException; 
    public ArrayList<SavedPage> getAllLinks();
    public ArrayList<T> getAllObjects();
    public boolean isLinkExist(String url);
    public boolean isContentLoaded(String url);
    public boolean isObjectParsed(String url);
    public long getObjectsAmount();
    public long initContentCursor();
    public SavedPage getNextContentItem();
    
}
