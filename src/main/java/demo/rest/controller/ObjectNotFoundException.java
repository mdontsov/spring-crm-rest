package demo.rest.controller;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundException(String message, Throwable cause, boolean enableSupression, boolean writeableStackTrace) {
        super(message, cause, enableSupression, writeableStackTrace);
    }
}
