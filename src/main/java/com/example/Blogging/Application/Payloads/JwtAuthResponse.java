package com.example.Blogging.Application.Payloads;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthResponse {

    private String token;
}
