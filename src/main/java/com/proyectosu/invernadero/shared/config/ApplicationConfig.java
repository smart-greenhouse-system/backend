package com.proyectosu.invernadero.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyectosu.invernadero.auth.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.auth.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.actuators.application.usecase.ExecuteActuatorUseCase;
import com.proyectosu.invernadero.actuators.domain.ports.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.alerts.application.usecase.CreateThresholdAlertUseCase;
import com.proyectosu.invernadero.alerts.application.usecase.ListAlertsUseCase;
import com.proyectosu.invernadero.alerts.domain.ports.AlertNotificationPort;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.inventory.application.usecase.RegistrarConsumoUseCase;
import com.proyectosu.invernadero.inventory.domain.ports.RegistroConsumoRepositoryPort;
import com.proyectosu.invernadero.notifications.center.application.usecase.DeleteNotificationUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.ListNotificationsUseCase;
import com.proyectosu.invernadero.notifications.center.application.usecase.UpdateNotificationStatusUseCase;
import com.proyectosu.invernadero.notifications.center.domain.ports.NotificationRepositoryPort;
import com.proyectosu.invernadero.notifications.application.usecase.UpdateNotificationPreferencesUseCase;
import com.proyectosu.invernadero.notifications.domain.ports.NotificationPreferencesRepositoryPort;
import com.proyectosu.invernadero.greenhouseconfig.application.usecase.GetGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.application.usecase.UpdateGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.domain.ports.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.thresholds.application.usecase.SaveThresholdRuleUseCase;
import com.proyectosu.invernadero.thresholds.domain.ports.ThresholdRuleRepositoryPort;
import com.proyectosu.invernadero.auth.domain.ports.JwtServicePort;
import com.proyectosu.invernadero.auth.domain.ports.PasswordEncoderPort;
import com.proyectosu.invernadero.auth.domain.ports.RoleRepositoryPort;
import com.proyectosu.invernadero.auth.domain.ports.UserRepositoryPort;
import com.proyectosu.invernadero.evento.application.usecase.GuardarEventoUseCase;
import com.proyectosu.invernadero.evento.application.usecase.ObtenerEventosUseCase;
import com.proyectosu.invernadero.evento.domain.ports.EventoRepositoryPort;
import com.proyectosu.invernadero.devices.application.usecase.ObtenerDispositivosUseCase;
import com.proyectosu.invernadero.devices.domain.ports.DispositivoRepositoryPort;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerHistoricoMedicionesUseCase;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerListadoMedicionesUseCase;
import com.proyectosu.invernadero.telemetria.application.usecase.ObtenerUltimaMedicionUseCase;
import com.proyectosu.invernadero.telemetria.application.usecase.RegistrarMedicionUseCase;
import com.proyectosu.invernadero.telemetria.domain.ports.MedicionTelemetriaRepositoryPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import org.springframework.beans.factory.ObjectProvider;

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
    public ObtenerDispositivosUseCase obtenerDispositivosUseCase(
            DispositivoRepositoryPort dispositivoRepositoryPort
    ) {
        return new ObtenerDispositivosUseCase(dispositivoRepositoryPort);
    }

    @Bean
    public ExecuteActuatorUseCase executeActuatorUseCase(
            DispositivoRepositoryPort dispositivoRepositoryPort,
            ActuatorEventRepositoryPort actuatorEventRepositoryPort,
            ObjectProvider<MqttPublisherPort> mqttPublisherPortProvider
    ) {
        return new ExecuteActuatorUseCase(
                dispositivoRepositoryPort,
                actuatorEventRepositoryPort,
                mqttPublisherPortProvider
        );
    }

    @Bean
    public RegistrarConsumoUseCase registrarConsumoUseCase(
            RegistroConsumoRepositoryPort registroConsumoRepositoryPort
    ) {
        return new RegistrarConsumoUseCase(registroConsumoRepositoryPort);
    }

        @Bean
        public UpdateNotificationPreferencesUseCase updateNotificationPreferencesUseCase(
            UserRepositoryPort userRepositoryPort,
            NotificationPreferencesRepositoryPort notificationPreferencesRepositoryPort
        ) {
        return new UpdateNotificationPreferencesUseCase(
            userRepositoryPort,
            notificationPreferencesRepositoryPort
        );
        }

            @Bean
            public CreateThresholdAlertUseCase createThresholdAlertUseCase(
                AlertRepositoryPort alertRepositoryPort,
                AlertNotificationPort alertNotificationPort
            ) {
            return new CreateThresholdAlertUseCase(
                alertRepositoryPort,
                alertNotificationPort
            );
            }

            @Bean
            public ListAlertsUseCase listAlertsUseCase(AlertRepositoryPort alertRepositoryPort) {
            return new ListAlertsUseCase(alertRepositoryPort);
            }

            @Bean
            public ListNotificationsUseCase listNotificationsUseCase(NotificationRepositoryPort notificationRepositoryPort) {
                return new ListNotificationsUseCase(notificationRepositoryPort);
            }

            @Bean
            public UpdateNotificationStatusUseCase updateNotificationStatusUseCase(NotificationRepositoryPort notificationRepositoryPort) {
                return new UpdateNotificationStatusUseCase(notificationRepositoryPort);
            }

            @Bean
            public DeleteNotificationUseCase deleteNotificationUseCase(NotificationRepositoryPort notificationRepositoryPort) {
                return new DeleteNotificationUseCase(notificationRepositoryPort);
            }

            @Bean
            public GetGreenhouseConfigUseCase getGreenhouseConfigUseCase(
                    GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort
            ) {
                return new GetGreenhouseConfigUseCase(greenhouseConfigRepositoryPort);
            }

            @Bean
            public UpdateGreenhouseConfigUseCase updateGreenhouseConfigUseCase(
                    GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort
            ) {
                return new UpdateGreenhouseConfigUseCase(greenhouseConfigRepositoryPort);
            }

            @Bean
            public SaveThresholdRuleUseCase saveThresholdRuleUseCase(
                    ThresholdRuleRepositoryPort thresholdRuleRepositoryPort
            ) {
            return new SaveThresholdRuleUseCase(thresholdRuleRepositoryPort);
            }

    @Bean
    public ObtenerUltimaMedicionUseCase obtenerUltimaMedicionUseCase(
            MedicionTelemetriaRepositoryPort telemetriaRepositoryPort
    ) {
        return new ObtenerUltimaMedicionUseCase(telemetriaRepositoryPort);
    }

        @Bean
        public RegistrarMedicionUseCase registrarMedicionUseCase(
            MedicionTelemetriaRepositoryPort telemetriaRepositoryPort
        ) {
        return new RegistrarMedicionUseCase(telemetriaRepositoryPort);
        }

    @Bean
    public ObtenerHistoricoMedicionesUseCase obtenerHistoricoMedicionesUseCase(
            MedicionTelemetriaRepositoryPort telemetriaRepositoryPort
    ) {
        return new ObtenerHistoricoMedicionesUseCase(telemetriaRepositoryPort);
    }

    @Bean
    public ObtenerListadoMedicionesUseCase obtenerListadoMedicionesUseCase(
            MedicionTelemetriaRepositoryPort telemetriaRepositoryPort
    ) {
        return new ObtenerListadoMedicionesUseCase(telemetriaRepositoryPort);
    }
}
