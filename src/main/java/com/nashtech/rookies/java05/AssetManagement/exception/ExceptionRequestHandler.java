package com.nashtech.rookies.java05.AssetManagement.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ErrorResponse;

@ControllerAdvice
public class ExceptionRequestHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
        String mess = ex.getMessage();
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), mess, new Date(), ex.getCause().toString());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        String mess = ex.getMessage();
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), mess,  new Date(), ex.getCause().toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
