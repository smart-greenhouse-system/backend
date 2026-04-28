package com.proyectosu.invernadero.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;
}
