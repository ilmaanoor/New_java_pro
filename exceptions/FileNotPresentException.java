package exceptions;

public class FileNotPresentException extends Exception {
    public FileNotPresentException() {
        super("File not found!");
    }
    public FileNotPresentException(String message) {
        super(message);
    }
}

