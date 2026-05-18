package com.proyectosu.invernadero.thresholds.inbound.controller;

import com.proyectosu.invernadero.shared.errores.ApiException;
import com.proyectosu.invernadero.shared.errores.GlobalExceptionHandler;
import com.proyectosu.invernadero.thresholds.application.usecase.SaveThresholdRuleUseCase;
import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;
import com.proyectosu.invernadero.thresholds.dto.request.ThresholdRuleRequest;
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

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ThresholdRulesControllerTest {

    @Mock
    private SaveThresholdRuleUseCase saveThresholdRuleUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ThresholdRulesController(saveThresholdRuleUseCase))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void shouldReturnCreatedWhenThresholdRuleIsSaved() throws Exception {
        when(saveThresholdRuleUseCase.ejecutar(any(ThresholdRuleRequest.class)))
                .thenReturn(sampleRule());

        mockMvc.perform(
                        post("/api/v1/threshold-rules")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "greenhouse_id": "gh-1",
                                          "variable": "temperature",
                                          "min_value": 18,
                                          "max_value": 30,
                                          "unit": "C",
                                          "severity": "critical",
                                          "enabled": true
                                        }
                                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Threshold rule created successfully"))
                .andExpect(jsonPath("$.rule_id").value("rule-1"));
    }

    @Test
    void shouldReturnApiErrorForInvalidRange() throws Exception {
        when(saveThresholdRuleUseCase.ejecutar(any(ThresholdRuleRequest.class)))
                .thenThrow(new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "INVALID_THRESHOLD_RANGE",
                        "Minimum value must be lower than maximum value"
                ));

        mockMvc.perform(
                        post("/api/v1/threshold-rules")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "greenhouse_id": "gh-1",
                                          "variable": "temperature",
                                          "min_value": 30,
                                          "max_value": 18,
                                          "unit": "C",
                                          "severity": "critical",
                                          "enabled": true
                                        }
                                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_THRESHOLD_RANGE"))
                .andExpect(jsonPath("$.message").value("Minimum value must be lower than maximum value"));
    }

    private ThresholdRule sampleRule() {
        return new ThresholdRule(
                "rule-1",
                "gh-1",
                "temperature",
                new BigDecimal("18"),
                new BigDecimal("30"),
                "C",
                "critical",
                true,
                Instant.parse("2026-05-17T00:00:00Z"),
                Instant.parse("2026-05-17T00:00:01Z")
        );
    }
}