package project.util.exception;

public class InvalidRequestException extends Exception {

    public InvalidRequestException() {}
    public InvalidRequestException(String msg) { super(msg); }
    public InvalidRequestException(String msg, Exception e) { super(msg, e); }
}
