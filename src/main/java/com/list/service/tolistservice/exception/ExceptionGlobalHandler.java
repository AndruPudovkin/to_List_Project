package com.list.service.tolistservice.exception;

import com.list.service.tolistservice.model.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "com.list.service.tolistservice")
public class ExceptionGlobalHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException e){
        log.error("Exception occurred {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "NOT_FOUND");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotValidDataException.class)
    public ResponseEntity<?> handleTaskValidDataException(TaskNotValidDataException e){
        log.error("Exception occurred {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "BED_REQUEST");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("Exception occurred {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "BED_REQUEST");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        log.error("Exception occurred {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "ERROR_SERVICE");
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}







































































































































