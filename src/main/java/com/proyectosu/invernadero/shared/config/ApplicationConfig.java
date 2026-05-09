package com.proyectosu.invernadero.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyectosu.invernadero.auth.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.auth.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.auth.domain.ports.JwtServicePort;
import com.proyectosu.invernadero.auth.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.auth.domain.ports.RoleRepositoryPort;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.evento.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.evento.domain.ports.EventoRepositoryPort;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerUltimaMedicionUseCase;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;

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
    public GuardarEventoUseCase guardarEventoUseCase(EventoRepositoryPort eventoRepositoryPort) {
       return new GuardarEventoUseCase(eventoRepositoryPort);
    }

    @Bean
    public ObtenerEventosUseCase obtenerEventosUseCase(EventoRepositoryPort eventoRepositoryPort) {
        return new ObtenerEventosUseCase(eventoRepositoryPort);
    }

    @Bean
    public ObtenerUltimaMedicionUseCase obtenerUltimaMedicionUseCase(
            MedicionTelemetriaRepositoryPort telemetriaRepositoryPort
    ) {
        return new ObtenerUltimaMedicionUseCase(telemetriaRepositoryPort);
    }
}
