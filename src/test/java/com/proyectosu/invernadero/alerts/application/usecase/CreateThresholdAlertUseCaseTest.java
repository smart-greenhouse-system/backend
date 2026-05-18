package com.proyectosu.invernadero.alerts.application.usecase;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertNotificationPort;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.alerts.dto.request.ThresholdAlertRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateThresholdAlertUseCaseTest {

    @Mock
    private AlertRepositoryPort alertRepositoryPort;

    @Mock
    private AlertNotificationPort alertNotificationPort;

    private CreateThresholdAlertUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateThresholdAlertUseCase(alertRepositoryPort, alertNotificationPort);
    }

    @Test
    void shouldStoreAlertAndMarkNotificationAsSent() {
        when(alertRepositoryPort.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ThresholdAlertRequest request = buildRequest();

        Alert result = useCase.ejecutar(request);

        assertNotNull(result.getId());
        assertEquals("gh-1", result.getGreenhouseId());
        assertEquals("temperature", result.getSource());
        assertEquals("warning", result.getSeverity());
        assertEquals("sent", result.getNotificationStatus());

        verify(alertNotificationPort).send(any(Alert.class));
        verify(alertRepositoryPort, times(2)).save(any(Alert.class));
    }

    @Test
    void shouldStoreAlertAndThrowWhenNotificationFails() {
        when(alertRepositoryPort.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("down")).when(alertNotificationPort).send(any(Alert.class));

        ThresholdAlertRequest request = buildRequest();

        ApiException exception = assertThrows(ApiException.class, () -> useCase.ejecutar(request));

        assertEquals("NOTIFICATION_SERVICE_ERROR", exception.getCode());
        verify(alertRepositoryPort, times(2)).save(any(Alert.class));
    }

    private ThresholdAlertRequest buildRequest() {
        ThresholdAlertRequest request = new ThresholdAlertRequest();
        request.setGreenhouse_id("gh-1");
        request.setSensor_reading_id("reading-1");
        request.setThreshold_rule_id("rule-1");
        request.setVariable("temperature");
        request.setValue(new BigDecimal("32.5"));
        request.setSeverity("warning");
        return request;
    }
}