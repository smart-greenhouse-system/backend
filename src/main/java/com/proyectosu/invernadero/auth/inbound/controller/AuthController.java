package com.proyectosu.invernadero.auth.inbound.controller;

import com.proyectosu.invernadero.auth.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.auth.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.auth.dto.request.LoginRequest;
import com.proyectosu.invernadero.auth.dto.request.RegisterRequest;
import com.proyectosu.invernadero.auth.dto.response.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final RegisterUserUseCase registerUseCase;
    private final LoginUserUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUseCase,
                          LoginUserUseCase loginUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        registerUseCase.execute(request.getEmail(), request.getPassword());

        return ResponseEntity.ok().body("Usuario registrado con exito");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse response = loginUseCase.execute(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(response);
    }

}
