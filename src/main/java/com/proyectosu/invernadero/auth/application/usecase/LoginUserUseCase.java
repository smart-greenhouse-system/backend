package com.proyectosu.invernadero.auth.application.usecase;

import com.proyectosu.invernadero.auth.domain.model.User;
import com.proyectosu.invernadero.auth.domain.ports.JwtServicePort;
import com.proyectosu.invernadero.auth.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.auth.dto.response.AuthResponse;

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
