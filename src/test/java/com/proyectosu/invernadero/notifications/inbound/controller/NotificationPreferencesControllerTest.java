package com.proyectosu.invernadero.notifications.inbound.controller;

import com.proyectosu.invernadero.notifications.application.usecase.UpdateNotificationPreferencesUseCase;
import com.proyectosu.invernadero.notifications.domain.model.NotificationPreferences;
import com.proyectosu.invernadero.shared.errores.ApiException;
import com.proyectosu.invernadero.shared.errores.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationPreferencesControllerTest {

  @Mock
  private UpdateNotificationPreferencesUseCase updateNotificationPreferencesUseCase;

    private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
      LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
      validator.afterPropertiesSet();

    mockMvc = MockMvcBuilders.standaloneSetup(
            new NotificationPreferencesController(updateNotificationPreferencesUseCase)
        )
        .setControllerAdvice(new GlobalExceptionHandler())
        .setValidator(validator)
        .build();
  }

    @Test
    void shouldReturnOkWhenPreferencesAreUpdated() throws Exception {
        String userId = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
    when(updateNotificationPreferencesUseCase.ejecutar(eq(userId), any()))
                .thenReturn(samplePreferences(userId));

        mockMvc.perform(
                        patch("/api/v1/users/{user_id}/notification-preferences", userId)
                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "events": {
                                            "critical_alerts": true,
                                            "warnings": true,
                                            "offline_sensors": false,
                                            "actuator_failures": true
                                          },
                                          "channels": {
                                            "push": true,
                                            "email": false,
                                            "in_app": true
                                          },
                                          "do_not_disturb": {
                                            "enabled": true,
                                            "start_time": "22:00",
                                            "end_time": "07:00"
                                          }
                                        }
                                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Notification preferences updated successfully"));
    }

    @Test
    void shouldReturnValidationErrorWhenChannelsAreMissing() throws Exception {
        String userId = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        mockMvc.perform(
                        patch("/api/v1/users/{user_id}/notification-preferences", userId)
              .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "events": {
                                            "critical_alerts": true,
                                            "warnings": true,
                                            "offline_sensors": false,
                                            "actuator_failures": true
                                          },
                                          "do_not_disturb": {
                                            "enabled": false,
                                            "start_time": "22:00",
                                            "end_time": "07:00"
                                          }
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("channels es obligatorio"));
    }

    @Test
    void shouldReturnApiErrorBodyWhenUseCaseRejectsConfiguration() throws Exception {
        String userId = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
      when(updateNotificationPreferencesUseCase.ejecutar(eq(userId), any()))
                .thenThrow(new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "INVALID_NOTIFICATION_CONFIGURATION",
                        "Invalid notification configuration"
                ));

        mockMvc.perform(
                        patch("/api/v1/users/{user_id}/notification-preferences", userId)
              .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "events": {
                                            "critical_alerts": true,
                                            "warnings": true,
                                            "offline_sensors": false,
                                            "actuator_failures": true
                                          },
                                          "channels": {
                                            "push": false,
                                            "email": false,
                                            "in_app": false
                                          },
                                          "do_not_disturb": {
                                            "enabled": false,
                                            "start_time": "22:00",
                                            "end_time": "07:00"
                                          }
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_NOTIFICATION_CONFIGURATION"))
                .andExpect(jsonPath("$.message").value("Invalid notification configuration"));
    }

    private NotificationPreferences samplePreferences(String userId) {
        return new NotificationPreferences(
                userId,
                true,
                true,
                false,
                true,
                true,
                false,
                true,
                true,
                "22:00",
                "07:00",
                Instant.parse("2026-05-17T00:00:00Z")
        );
    }
}