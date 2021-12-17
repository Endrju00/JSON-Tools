package pl.put.poznan.transformer.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidJson.class)
    public ResponseEntity<Object> handleInvalidJson(InvalidJson ex) {
        return new ResponseEntity<Object>(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<Object> handleNoSuchMethodException(NoSuchMethodException ex) {
        return new ResponseEntity<Object>(new ApiError(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED, LocalDateTime.now()), HttpStatus.NOT_IMPLEMENTED);
    }
}
