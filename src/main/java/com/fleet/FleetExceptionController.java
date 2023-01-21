package com.fleet;

import com.fleet.common.exceptions.ApiError;
import com.fleet.common.exceptions.LowBatteryException;
import com.fleet.common.exceptions.WeightExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FleetExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = LowBatteryException.class)
    public ResponseEntity<Object> exception(LowBatteryException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = WeightExceededException.class)
    public ResponseEntity<Object> exception(WeightExceededException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}