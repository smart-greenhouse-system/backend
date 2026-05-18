package com.proyectosu.invernadero.alerts.application.usecase;

import com.proyectosu.invernadero.alerts.domain.model.Alert;
import com.proyectosu.invernadero.alerts.domain.ports.AlertRepositoryPort;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAlertsUseCaseTest {

    @Mock
    private AlertRepositoryPort alertRepositoryPort;

    private ListAlertsUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListAlertsUseCase(alertRepositoryPort);
    }

    @Test
    void shouldFilterByGreenhouseAndDateAndSortDescending() {
        when(alertRepositoryPort.findAll()).thenReturn(List.of(
                buildAlert("alert-1", "gh-1", "2026-05-10T10:00:00Z"),
                buildAlert("alert-2", "gh-1", "2026-05-12T10:00:00Z"),
                buildAlert("alert-3", "gh-2", "2026-05-13T10:00:00Z")
        ));

        List<Alert> alerts = useCase.ejecutar("gh-1", "2026-05-01", "2026-05-31");

        assertEquals(2, alerts.size());
        assertEquals("alert-2", alerts.get(0).getId());
        assertEquals("alert-1", alerts.get(1).getId());
    }

    @Test
    void shouldRejectInvalidDateRange() {
        ApiException exception = assertThrows(ApiException.class, () -> useCase.ejecutar("gh-1", "2026-05-31", "2026-05-01"));

        assertEquals("INVALID_ALERT_FILTER", exception.getCode());
    }

    private Alert buildAlert(String id, String greenhouseId, String timestamp) {
        return new Alert(
                id,
                greenhouseId,
                "reading-1",
                "rule-1",
                "temperature",
                "Threshold exceeded for temperature: 32.5",
                "warning",
                new BigDecimal("32.5"),
                "sent",
                Instant.parse(timestamp)
        );
    }
}