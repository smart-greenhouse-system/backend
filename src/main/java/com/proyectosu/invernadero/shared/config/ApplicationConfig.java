package com.proyectosu.invernadero.shared.config;

import com.proyectosu.invernadero.actuator.application.usecases.ExecuteTimedActuatorUseCase;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorResolverPort;
import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.auth.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.evento.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.auth.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.auth.domain.ports.*;
import com.proyectosu.invernadero.evento.domain.ports.EventoRepositoryPort;
import com.proyectosu.invernadero.prediction.application.usecases.ProcessAiPredictionUseCase;
import com.proyectosu.invernadero.prediction.domain.port.GreenhouseConfigReaderPort;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import com.proyectosu.invernadero.prediction.domain.port.TimedActuatorExecutorPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

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
    public ProcessAiPredictionUseCase processAiPredictionUseCase(
            PredictionRepositoryPort predictionRepositoryPort,
            GreenhouseConfigReaderPort greenhouseConfigReaderPort,
            TimedActuatorExecutorPort timedActuatorExecutorPort
    ) {
        return new ProcessAiPredictionUseCase(
                predictionRepositoryPort,
                greenhouseConfigReaderPort,
                timedActuatorExecutorPort
        );
    }

    @Bean
    public TimedActuatorExecutorPort timedActuatorExecutorPort(
            ActuatorResolverPort actuatorResolverPort,
            ActuatorEventRepositoryPort actuatorEventRepositoryPort,
            MqttPublisherPort mqttPublisherPort,
            TaskScheduler taskScheduler
    ) {
        return new ExecuteTimedActuatorUseCase(
                actuatorResolverPort,
                actuatorEventRepositoryPort,
                mqttPublisherPort,
                taskScheduler
        );
    }

}
