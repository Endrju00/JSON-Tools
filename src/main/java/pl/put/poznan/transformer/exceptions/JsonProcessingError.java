package pl.put.poznan.transformer.exceptions;

public class JsonProcessingError extends RuntimeException {
    public JsonProcessingError(String message) {
        super(message);
    }

    public JsonProcessingError(String message, Throwable cause) {
        super(message, cause);
    }
}
