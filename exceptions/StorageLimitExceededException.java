package exceptions;

public class StorageLimitExceededException extends Exception {
    public StorageLimitExceededException() {
        super("Storage limit exceeded!");
    }
    public StorageLimitExceededException(String message) {
        super(message);
    }
}
