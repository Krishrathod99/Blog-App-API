package com.example.Blogging.Application.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    // this class need globalExceptionHandler class for display message in postman console
    String resourceName;
    String fieldName;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s Not found with %s : %s" , resourceName , fieldName , fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
