package com.proyectosu.invernadero.greenhouse.application.usecases;

import com.proyectosu.invernadero.greenhouse.application.command.UpdateGreenhouseConfigCommand;
import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.domain.port.GreenhouseConfigRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateGreenhouseConfigUseCase {

    private static final int MIN_AI_ANALYSIS_FREQUENCY_MINUTES = 1;
    private static final int MAX_AI_ANALYSIS_FREQUENCY_MINUTES = 1440;

    private final GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort;

    public GreenhouseConfig execute(UpdateGreenhouseConfigCommand command) {
        validateCommand(command);

        GreenhouseConfig currentConfig = greenhouseConfigRepositoryPort.getCurrentOrCreateDefault();

        GreenhouseConfig updatedConfig = currentConfig.applyPartialUpdate(
                command.getGreenhouseName(),
                command.getAutomaticMode(),
                command.getAiAnalysisFrequencyMinutes()
        );

        return greenhouseConfigRepositoryPort.save(updatedConfig);
    }

    private void validateCommand(UpdateGreenhouseConfigCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de configuración no puede ser nulo");
        }

        if (command.getGreenhouseName() == null
                && command.getAutomaticMode() == null
                && command.getAiAnalysisFrequencyMinutes() == null) {
            throw new IllegalArgumentException("Debe enviar al menos un campo para actualizar");
        }

        if (command.getGreenhouseName() != null && isBlank(command.getGreenhouseName())) {
            throw new IllegalArgumentException("El nombre del invernadero no puede estar vacío");
        }

        if (command.getAiAnalysisFrequencyMinutes() != null) {
            if (command.getAiAnalysisFrequencyMinutes() < MIN_AI_ANALYSIS_FREQUENCY_MINUTES) {
                throw new IllegalArgumentException("La frecuencia de análisis IA debe ser mayor a 0 minutos");
            }

            if (command.getAiAnalysisFrequencyMinutes() > MAX_AI_ANALYSIS_FREQUENCY_MINUTES) {
                throw new IllegalArgumentException("La frecuencia de análisis IA no puede superar 1440 minutos");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
