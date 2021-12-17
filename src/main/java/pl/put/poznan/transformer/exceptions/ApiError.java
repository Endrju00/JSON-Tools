package pl.put.poznan.transformer.exceptions;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class ApiError {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timeStamp;

    public ApiError(String message, HttpStatus status, LocalDateTime timeStamp) {
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
