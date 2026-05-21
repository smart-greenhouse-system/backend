package com.proyectosu.invernadero.ai.application.usecases;

import com.proyectosu.invernadero.ai.application.command.AnalyzeImageCommand;
import com.proyectosu.invernadero.ai.domain.model.AiImagePredictionResult;
import com.proyectosu.invernadero.ai.domain.port.AiPredictionClientPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AnalyzeImageUseCase {

    private final AiPredictionClientPort aiPredictionClientPort;

    public AiImagePredictionResult execute(AnalyzeImageCommand command) {
        validateCommand(command);

        AiImagePredictionResult result = aiPredictionClientPort.predict(command.getImageBase64());

        log.info(
                "Predicción IA recibida para dispositivo [{}]: cultivo={}, estado_planta={}, confianza={}, tiempo_cosecha_dias={}",
                command.getDeviceId(),
                result.getCultivo(),
                result.getEstadoPlanta(),
                result.getConfianza(),
                result.getTiempoCosechaDias()
        );

        return result;
    }

    private void validateCommand(AnalyzeImageCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de análisis de imagen no puede ser nulo");
        }

        if (isBlank(command.getImageBase64())) {
            throw new IllegalArgumentException("La imagen es obligatoria");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
