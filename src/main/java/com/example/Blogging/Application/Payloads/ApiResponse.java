package com.example.Blogging.Application.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    // This class is used for displaying the message which was created by ResourceNotFoundException class or any other class into postman console
    private String message;
    private boolean success;

}
