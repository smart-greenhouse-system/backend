package com.proyectosu.invernadero.ia.application.usecase;

import com.proyectosu.invernadero.ia.domain.model.AiModelType;
import com.proyectosu.invernadero.ia.dto.AiPredictionRequest;
import com.proyectosu.invernadero.ia.dto.AiPredictionResponse;
import com.proyectosu.invernadero.ia.infraestructure.client.AiHttpClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class PredictImageUseCase {

    private final AiHttpClient aiHttpClient;

    public PredictImageUseCase(AiHttpClient aiHttpClient) {
        this.aiHttpClient = aiHttpClient;
    }

    public AiPredictionResponse execute(String model, AiPredictionRequest request) {
        AiModelType modelType = AiModelType.from(model);
        Map<String, Object> rawResponse = aiHttpClient.predict(modelType, request);

        return new AiPredictionResponse(
                true,
                modelType.getPath(),
                request.deviceId(),
                rawResponse,
                "Predicción IA procesada correctamente",
                Instant.now(),
                rawResponse
        );
    }
}
