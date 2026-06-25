package com.proyectosu.invernadero.prediction.application.usecases;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLatestImageAnalysisPredictionUseCase {

    private final PredictionRepositoryPort predictionRepositoryPort;

    public Prediction execute() {
        return predictionRepositoryPort.findLatestImageAnalysis()
                .orElseThrow(() -> new IllegalArgumentException("No hay análisis de imagen registrados"));
    }
}
