package parser.interfaces;

import parser.exceptions.ParseErrorException;




public interface ObjectParser<T> {
    public T getObjectFromString(String content,String url) throws ParseErrorException;
     
}
