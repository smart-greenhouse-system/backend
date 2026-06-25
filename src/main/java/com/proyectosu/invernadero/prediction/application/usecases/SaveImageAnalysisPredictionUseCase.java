package com.proyectosu.invernadero.prediction.application.usecases;

import com.proyectosu.invernadero.prediction.application.command.SaveImageAnalysisPredictionCommand;
import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveImageAnalysisPredictionUseCase {

    private final PredictionRepositoryPort predictionRepositoryPort;

    public Prediction execute(SaveImageAnalysisPredictionCommand command) {
        validateCommand(command);

        Prediction prediction = Prediction.createFromImageAnalysis(
                command.getDeviceId(),
                command.getCultivo(),
                command.isSuccess(),
                command.getEstadoPlanta(),
                command.getConfianza(),
                command.getTiempoCosechaDias()
        );

        return predictionRepositoryPort.save(prediction);
    }

    private void validateCommand(SaveImageAnalysisPredictionCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de análisis de imagen no puede ser nulo");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (!command.isSuccess()) {
            throw new IllegalArgumentException("No se puede guardar un análisis de imagen fallido");
        }

        if (isBlank(command.getCultivo())) {
            throw new IllegalArgumentException("El cultivo es obligatorio");
        }

        if (isBlank(command.getEstadoPlanta())) {
            throw new IllegalArgumentException("El estado de la planta es obligatorio");
        }

        if (command.getConfianza() == null) {
            throw new IllegalArgumentException("La confianza es obligatoria");
        }

        if (command.getTiempoCosechaDias() == null) {
            throw new IllegalArgumentException("El tiempo de cosecha es obligatorio");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
