package project.util.exception;

/**
 * File system does not contain the specified file
 */
public class FileNotFoundException extends Exception {
    public FileNotFoundException() { super(); }
    public FileNotFoundException(String message) { super(message); }
    public FileNotFoundException(String message, Throwable e) { super(message, e); }
}