package backend.adapters.driving.exception;

public class DataTypeNotSupportedException extends RuntimeException {
    public DataTypeNotSupportedException(String message) {
        super(message);
    }
}
