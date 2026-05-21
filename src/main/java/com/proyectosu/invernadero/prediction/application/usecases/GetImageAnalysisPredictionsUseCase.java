package com.proyectosu.invernadero.prediction.application.usecases;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetImageAnalysisPredictionsUseCase {

    private final PredictionRepositoryPort predictionRepositoryPort;

    public List<Prediction> execute() {
        return predictionRepositoryPort.findImageAnalysisHistory();
    }
}
