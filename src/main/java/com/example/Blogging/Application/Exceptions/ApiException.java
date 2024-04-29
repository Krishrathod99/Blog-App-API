package com.example.Blogging.Application.Exceptions;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
    public ApiException( ) {
        super();
    }
}
