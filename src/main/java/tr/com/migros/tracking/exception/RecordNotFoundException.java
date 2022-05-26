package tr.com.migros.tracking.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(final String message) {
        super(message);
    }
}
