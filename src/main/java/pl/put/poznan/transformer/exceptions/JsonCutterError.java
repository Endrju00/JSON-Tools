package pl.put.poznan.transformer.exceptions;

public class JsonCutterError extends RuntimeException {
    public JsonCutterError(String message) {
        super(message);
    }

    public JsonCutterError(String message, Throwable cause) {
        super(message, cause);
    }
}
