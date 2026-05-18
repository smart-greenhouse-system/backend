package com.proyectosu.invernadero.greenhouseconfig.application.usecase;

import com.proyectosu.invernadero.greenhouseconfig.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouseconfig.domain.ports.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.greenhouseconfig.dto.request.GreenhouseConfigRequest;
import com.proyectosu.invernadero.shared.errores.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateGreenhouseConfigUseCaseTest {

    @Mock
    private GreenhouseConfigRepositoryPort repositoryPort;

    private UpdateGreenhouseConfigUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateGreenhouseConfigUseCase(repositoryPort);
    }

    @Test
    void shouldUpdatePartialConfig() {
        when(repositoryPort.read()).thenReturn(new GreenhouseConfig(
                "global",
                false,
                10,
                10,
                "UTC",
                Instant.parse("2026-05-18T00:00:00Z")
        ));
        when(repositoryPort.save(any(GreenhouseConfig.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GreenhouseConfigRequest request = new GreenhouseConfigRequest();
        request.setAutomatic_mode(true);
        request.setInactivity_threshold_minutes(15);

        GreenhouseConfig result = useCase.ejecutar(request);

        assertEquals(true, result.getAutomaticMode());
        assertEquals(15, result.getInactivityThresholdMinutes());
        assertEquals(10, result.getManualOverrideDurationMinutes());
        assertEquals("UTC", result.getReportTimezone());
    }

    @Test
    void shouldRejectInvalidTimezone() {
        when(repositoryPort.read()).thenReturn(new GreenhouseConfig(
                "global",
                false,
                10,
                10,
                "UTC",
                Instant.parse("2026-05-18T00:00:00Z")
        ));

        GreenhouseConfigRequest request = new GreenhouseConfigRequest();
        request.setReport_timezone("bad-zone");

        assertThrows(ApiException.class, () -> useCase.ejecutar(request));
    }
}