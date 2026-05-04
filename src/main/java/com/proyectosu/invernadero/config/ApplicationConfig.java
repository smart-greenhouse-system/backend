package com.proyectosu.invernadero.config;

import com.proyectosu.invernadero.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.domain.ports.*;
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

    @Bean
    public GuardarEventoUseCase guardarEventoUseCase(EventoRepositoryPort  eventoRepositoryPort) {
       return new GuardarEventoUseCase(eventoRepositoryPort);
    }

    @Bean
    public ObtenerEventosUseCase obtenerEventosUseCase(EventoRepositoryPort eventoRepositoryPort) {
        return new ObtenerEventosUseCase(eventoRepositoryPort);
    }
}
