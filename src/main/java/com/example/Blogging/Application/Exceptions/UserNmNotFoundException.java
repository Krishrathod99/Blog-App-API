package com.example.Blogging.Application.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNmNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    String fieldValue;

    public UserNmNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s Not found with %s : %s", resourceName , fieldName , fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }


}
