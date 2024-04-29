package com.example.Blogging.Application.Payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtAuthRequest {
    private String userName;     // Actually username is email
    private String password;
}
