package com.proyectosu.invernadero.application.usecase;

import com.proyectosu.invernadero.domain.model.auth.User;
import com.proyectosu.invernadero.domain.ports.JwtServicePort;
import com.proyectosu.invernadero.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.rest.dto.response.AuthResponse;

public class LoginUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtServicePort jwtService;

    public LoginUserUseCase(UserRepositoryPort userRepository,
                            PasswordEncoderPort passwordEncoder,
                            JwtServicePort jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse execute(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales invalidas"));

        if (!user.isActive()) {
            throw new RuntimeException("el usuario no esta activo");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationInSeconds()
        );
    }
}
