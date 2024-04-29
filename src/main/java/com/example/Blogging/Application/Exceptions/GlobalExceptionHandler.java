package com.example.Blogging.Application.Exceptions;

import com.example.Blogging.Application.Payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice      // make this class exception handler, this handle all the global exceptions of controller
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)      // which class you want to handle, write it with adding commas
    public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.NOT_FOUND);
    }

//    This method is for validation of all user field and provide error message and suggestion, if client enter invalid & improper data
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){

        Map<String , String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {      // through this error, we can fetch all fields and their messages which was already declared in userDTO
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName , message);
        });

        return new ResponseEntity<Map<String , String>>(resp , HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ApiException.class)      // which class you want to handle, write it with adding commas
    public ResponseEntity<ApiResponse> handleApiExceptionHandler(ApiException ex){
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.BAD_REQUEST);
    }
}
