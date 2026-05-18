package com.proyectosu.invernadero.greenhouseconfig.inbound.controller;

import com.proyectosu.invernadero.greenhouseconfig.application.usecase.GetGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.application.usecase.UpdateGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.dto.request.GreenhouseConfigRequest;
import com.proyectosu.invernadero.shared.errores.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GreenhouseConfigControllerTest {

    @Mock
    private GetGreenhouseConfigUseCase getGreenhouseConfigUseCase;

    @Mock
    private UpdateGreenhouseConfigUseCase updateGreenhouseConfigUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(
                        new GreenhouseConfigController(getGreenhouseConfigUseCase, updateGreenhouseConfigUseCase)
                )
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldReturnConfig() throws Exception {
        when(getGreenhouseConfigUseCase.ejecutar()).thenReturn(sampleConfig());

        mockMvc.perform(get("/api/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automatic_mode").value(false))
                .andExpect(jsonPath("$.inactivity_threshold_minutes").value(10));
    }

    @Test
    void shouldUpdateConfig() throws Exception {
        when(updateGreenhouseConfigUseCase.ejecutar(any(GreenhouseConfigRequest.class))).thenReturn(updatedConfig());

        mockMvc.perform(patch("/api/config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "automatic_mode": true,
                                  "inactivity_threshold_minutes": 15,
                                  "manual_override_duration_minutes": 20,
                                  "report_timezone": "UTC"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automatic_mode").value(true))
                .andExpect(jsonPath("$.inactivity_threshold_minutes").value(15));
    }

    private GreenhouseConfig sampleConfig() {
        return new GreenhouseConfig("global", false, 10, 10, "UTC", Instant.parse("2026-05-18T00:00:00Z"));
    }

    private GreenhouseConfig updatedConfig() {
        return new GreenhouseConfig("global", true, 15, 20, "UTC", Instant.parse("2026-05-18T00:00:00Z"));
    }
}