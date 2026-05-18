package com.proyectosu.invernadero.alerts.inbound.controller;

import com.proyectosu.invernadero.alerts.application.usecase.CreateThresholdAlertUseCase;
import com.proyectosu.invernadero.alerts.application.usecase.ListAlertsUseCase;
import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.dto.request.ThresholdAlertRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import com.proyectosu.invernadero.shared.errores.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AlertsControllerTest {

    @Mock
    private CreateThresholdAlertUseCase createThresholdAlertUseCase;

    @Mock
    private ListAlertsUseCase listAlertsUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new AlertsController(createThresholdAlertUseCase, listAlertsUseCase))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldListAlertsForFrontend() throws Exception {
        when(listAlertsUseCase.ejecutar(eq("gh-1"), eq("2026-05-01"), eq("2026-05-31")))
                .thenReturn(List.of(sampleAlert("alert-1"), sampleAlert("alert-2")));

        mockMvc.perform(get("/api/v1/alerts")
                        .param("greenhouse_id", "gh-1")
                        .param("from", "2026-05-01")
                        .param("to", "2026-05-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alerts").isArray())
                .andExpect(jsonPath("$.alerts[0].alert_id").value("alert-1"));
    }

    @Test
    void shouldCreateThresholdAlert() throws Exception {
        when(createThresholdAlertUseCase.ejecutar(any(ThresholdAlertRequest.class)))
                .thenReturn(sampleAlert("alert-1"));

        mockMvc.perform(post("/api/v1/alerts/threshold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "greenhouse_id": "gh-1",
                                  "sensor_reading_id": "reading-1",
                                  "threshold_rule_id": "rule-1",
                                  "variable": "temperature",
                                  "value": 32.5,
                                  "severity": "warning"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Threshold alert generated successfully"))
                .andExpect(jsonPath("$.alert_id").value("alert-1"))
                .andExpect(jsonPath("$.notification_status").value("sent"));
    }

    @Test
    void shouldReturnNotificationServiceErrorWhenUseCaseFails() throws Exception {
        when(createThresholdAlertUseCase.ejecutar(any(ThresholdAlertRequest.class)))
                .thenThrow(new ApiException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "NOTIFICATION_SERVICE_ERROR",
                        "Alert was stored but notification could not be delivered"
                ));

        mockMvc.perform(post("/api/v1/alerts/threshold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "greenhouse_id": "gh-1",
                                  "sensor_reading_id": "reading-1",
                                  "threshold_rule_id": "rule-1",
                                  "variable": "temperature",
                                  "value": 32.5,
                                  "severity": "warning"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("NOTIFICATION_SERVICE_ERROR"));
    }

    private Alert sampleAlert(String alertId) {
        return new Alert(
                alertId,
                "gh-1",
                "reading-1",
                "rule-1",
                "temperature",
                "Threshold exceeded for temperature: 32.5",
                "warning",
                new BigDecimal("32.5"),
                "sent",
                Instant.parse("2026-05-17T10:00:00Z")
        );
    }
}