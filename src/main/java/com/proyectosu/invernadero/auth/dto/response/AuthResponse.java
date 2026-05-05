package com.proyectosu.invernadero.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;

    public AuthResponse(String accessToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

}
