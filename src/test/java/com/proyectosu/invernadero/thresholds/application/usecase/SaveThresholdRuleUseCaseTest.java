package com.proyectosu.invernadero.thresholds.application.usecase;

import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;
import com.proyectosu.invernadero.thresholds.domain.ports.ThresholdRuleRepositoryPort;
import com.proyectosu.invernadero.thresholds.dto.request.ThresholdRuleRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveThresholdRuleUseCaseTest {

    @Mock
    private ThresholdRuleRepositoryPort thresholdRuleRepositoryPort;

    private SaveThresholdRuleUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new SaveThresholdRuleUseCase(thresholdRuleRepositoryPort);
    }

    @Test
    void shouldCreateThresholdRuleWhenRangeIsValid() {
        when(thresholdRuleRepositoryPort.findByGreenhouseIdAndVariable("gh-1", "temperature"))
                .thenReturn(Optional.empty());
        when(thresholdRuleRepositoryPort.save(any(ThresholdRule.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ThresholdRuleRequest request = buildRequest(
                "gh-1",
                "temperature",
                new BigDecimal("18"),
                new BigDecimal("30"),
                "C",
                "critical",
                true
        );

        ThresholdRule result = useCase.ejecutar(request);

        assertEquals("gh-1", result.getGreenhouseId());
        assertEquals("temperature", result.getVariable());
        assertEquals(new BigDecimal("18"), result.getMinValue());
        assertEquals(new BigDecimal("30"), result.getMaxValue());
        assertEquals("C", result.getUnit());
        assertEquals("critical", result.getSeverity());
        assertEquals(true, result.isEnabled());

        verify(thresholdRuleRepositoryPort).save(any(ThresholdRule.class));
    }

    @Test
    void shouldUpdateExistingThresholdRuleInsteadOfDuplicating() {
        ThresholdRule existing = new ThresholdRule(
                "rule-1",
                "gh-1",
                "temperature",
                new BigDecimal("18"),
                new BigDecimal("30"),
                "C",
                "warning",
                true,
                Instant.parse("2026-05-17T00:00:00Z"),
                Instant.parse("2026-05-17T00:00:00Z")
        );
        when(thresholdRuleRepositoryPort.findByGreenhouseIdAndVariable("gh-1", "temperature"))
                .thenReturn(Optional.of(existing));
        when(thresholdRuleRepositoryPort.save(any(ThresholdRule.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ThresholdRuleRequest request = buildRequest(
                "gh-1",
                "temperature",
                new BigDecimal("19"),
                new BigDecimal("31"),
                "C",
                "critical",
                false
        );

        ThresholdRule result = useCase.ejecutar(request);

        assertEquals("rule-1", result.getId());
        assertEquals(new BigDecimal("19"), result.getMinValue());
        assertEquals(new BigDecimal("31"), result.getMaxValue());
        assertFalse(result.isEnabled());
    }

    @Test
    void shouldRejectInvalidRange() {
        ThresholdRuleRequest request = buildRequest(
                "gh-1",
                "temperature",
                new BigDecimal("30"),
                new BigDecimal("18"),
                "C",
                "critical",
                true
        );

        ApiException exception = assertThrows(ApiException.class, () -> useCase.ejecutar(request));

        assertEquals("INVALID_THRESHOLD_RANGE", exception.getCode());
    }

    private ThresholdRuleRequest buildRequest(
            String greenhouseId,
            String variable,
            BigDecimal minValue,
            BigDecimal maxValue,
            String unit,
            String severity,
            boolean enabled
    ) {
        ThresholdRuleRequest request = new ThresholdRuleRequest();
        request.setGreenhouse_id(greenhouseId);
        request.setVariable(variable);
        request.setMin_value(minValue);
        request.setMax_value(maxValue);
        request.setUnit(unit);
        request.setSeverity(severity);
        request.setEnabled(enabled);
        return request;
    }
}