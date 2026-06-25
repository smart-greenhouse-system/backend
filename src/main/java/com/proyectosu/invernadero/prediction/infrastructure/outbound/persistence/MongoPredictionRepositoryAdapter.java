package com.proyectosu.invernadero.prediction.infrastructure.outbound.persistence;

import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import com.proyectosu.invernadero.prediction.infrastructure.outbound.mappers.PredictionPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoPredictionRepositoryAdapter implements PredictionRepositoryPort {

    private final MongoPredictionRepository mongoPredictionRepository;
    private final PredictionPersistenceMapper predictionPersistenceMapper;

    @Override
    public Prediction save(Prediction prediction) {
        MongoPredictionDocument document = predictionPersistenceMapper.toDocument(prediction);
        MongoPredictionDocument savedDocument = mongoPredictionRepository.save(document);
        return predictionPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public Prediction update(Prediction prediction) {
        MongoPredictionDocument document = predictionPersistenceMapper.toDocument(prediction);
        MongoPredictionDocument savedDocument = mongoPredictionRepository.save(document);
        return predictionPersistenceMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Prediction> findById(String id) {
        return mongoPredictionRepository.findById(id)
                .map(predictionPersistenceMapper::toDomain);
    }

    @Override
    public List<Prediction> findAll() {
        return mongoPredictionRepository.findAll()
                .stream()
                .map(predictionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Prediction> findLatestImageAnalysis() {
        return mongoPredictionRepository.findFirstByTipoOrderByCreatedAtDesc(Prediction.TYPE_IMAGE_ANALYSIS)
                .map(predictionPersistenceMapper::toDomain);
    }

    @Override
    public List<Prediction> findImageAnalysisHistory() {
        return mongoPredictionRepository.findByTipoOrderByCreatedAtDesc(Prediction.TYPE_IMAGE_ANALYSIS)
                .stream()
                .map(predictionPersistenceMapper::toDomain)
                .toList();
    }
}
