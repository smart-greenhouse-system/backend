package com.proyectosu.invernadero.config;

import com.proyectosu.invernadero.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.domain.ports.JwtServicePort;
import com.proyectosu.invernadero.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.domain.ports.RoleRepositoryPort;
import com.proyectosu.invernadero.domain.ports.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            RoleRepositoryPort roleRepository
    ) {
        return new RegisterUserUseCase(userRepository, passwordEncoder, roleRepository);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtServicePort jwtService
    ) {
        return new LoginUserUseCase(
                userRepository,
                passwordEncoder,
                jwtService
        );
    }
}
