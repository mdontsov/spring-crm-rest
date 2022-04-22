package demo.rest.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // <- Global exception handler
public class RESTExceptionHandler {

    // add exception handling

    // Exception handler method of type ControllerErrorResponse with ObjectNotFoundException to catch
    @ExceptionHandler
    public ResponseEntity<ControllerErrorResponse> handleException(@NotNull ObjectNotFoundException exception) {

        // create a ControllerErrorResponse object
        ControllerErrorResponse errorResponse = new ControllerErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // <- Jackson will convert it to JSON
    }

    // Exception handler to catch any exception
    @ExceptionHandler
    public ResponseEntity<ControllerErrorResponse> handleException(@NotNull Exception exception) {

        // create a ControllerErrorResponse object
        ControllerErrorResponse errorResponse = new ControllerErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // <- Jackson will convert it to JSON
    }
}
