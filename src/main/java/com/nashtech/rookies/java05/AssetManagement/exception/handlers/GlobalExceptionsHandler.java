package com.nashtech.rookies.java05.AssetManagement.exception.handlers;

import com.nashtech.rookies.java05.AssetManagement.dto.response.ErrorResponse;
import com.nashtech.rookies.java05.AssetManagement.exception.*;
import com.nashtech.rookies.java05.AssetManagement.exception.ForbiddenException;
import com.nashtech.rookies.java05.AssetManagement.exception.PasswordException;
import com.nashtech.rookies.java05.AssetManagement.exception.ResourceCheckDateException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(RuntimeException exception,
                                                                           WebRequest request) {
        ErrorResponse error = new ErrorResponse("400", exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse error = new ErrorResponse("400", "Validation Error", new Date(), errors.toString());
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ResourceCheckDateException.class})
    protected ResponseEntity<ErrorResponse> handleCategoryNotAcceptException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse("400", exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), exception.getMessage(),
                new Date(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                exception.getMessage(), new Date(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> forbiddenExceptionHandling(ForbiddenException forbiddenException,
                                                             WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.toString(),
                forbiddenException.getMessage(), new Date(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ ResourceCategoryException.class })
    protected ResponseEntity<ErrorResponse> handleCategoryException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse("417", exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({ ResourceAssetException.class })
    protected ResponseEntity<ErrorResponse> handleAssetException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse("417", exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({ ResourceCheckException.class })
    protected ResponseEntity<ErrorResponse> handleNotFoundAssetException(RuntimeException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse("400", exception.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    
    @ExceptionHandler({PasswordException.class})
    public ResponseEntity<PasswordException> handlePasswordException(PasswordException exception) {
        return ResponseEntity.badRequest().body(new PasswordException(exception.getPasswordError()));
    }
}
