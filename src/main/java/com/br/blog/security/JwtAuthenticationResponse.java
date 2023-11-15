package com.br.blog.security;

import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {

    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
