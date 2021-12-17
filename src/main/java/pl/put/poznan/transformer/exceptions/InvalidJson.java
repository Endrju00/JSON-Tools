package pl.put.poznan.transformer.exceptions;

public class InvalidJson extends RuntimeException {
    public InvalidJson(String message) {
        super(message);
    }

    public InvalidJson(String message, Throwable cause) {
        super(message, cause);
    }
}
