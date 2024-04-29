package com.example.Blogging.Application.Payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
                            // Validation annotation like NotNull, Email etc..
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be contain minimum 4 characters  !!")
    private String name;

    @Email(message = "Email address isn't Valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password length must be greater than 3 characters and less than 10 Character !!")
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDTO> roles = new HashSet<>();

}
