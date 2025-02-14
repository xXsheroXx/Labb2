package model.exceptions;

public class TitleNotUniqueException extends RuntimeException{
    public TitleNotUniqueException() {
        super("Title is not unique");
    }

    public TitleNotUniqueException(String msg) {
        super(msg);
    }
}
