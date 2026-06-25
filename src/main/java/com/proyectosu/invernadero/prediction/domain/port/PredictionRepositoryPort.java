package com.proyectosu.invernadero.prediction.domain.port;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;

import java.util.List;
import java.util.Optional;

public interface PredictionRepositoryPort {
    Prediction save(Prediction prediction);

    Prediction update(Prediction prediction);

    Optional<Prediction> findById(String id);

    List<Prediction> findAll();

    Optional<Prediction> findLatestImageAnalysis();

    List<Prediction> findImageAnalysisHistory();
}
