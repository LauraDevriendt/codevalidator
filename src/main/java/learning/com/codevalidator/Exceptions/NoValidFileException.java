package learning.com.codevalidator.Exceptions;

public class NoValidFileException extends RuntimeException{
    public NoValidFileException(String message) {
        super(message);
    }
}
