package com.proyectosu.invernadero.ia.infraestructure.client;

import com.proyectosu.invernadero.ia.domain.model.AiModelType;
import com.proyectosu.invernadero.ia.dto.AiPredictionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AiHttpClient {

    private final RestTemplate restTemplate;
    private final String aiBaseUrl;

    public AiHttpClient(
            RestTemplate restTemplate,
            @Value("${external.ai.base-url:http://localhost:5000}") String aiBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.aiBaseUrl = aiBaseUrl;
    }

    public Map<String, Object> predict(AiModelType modelType, AiPredictionRequest request) {
        String url = aiBaseUrl + "/" + modelType.getPath() + "/predict";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, Map.of("image", request.image()), Map.class);
            return response.getBody();
        } catch (RestClientException exception) {
            throw new IllegalStateException("Error consumiendo API IA: " + exception.getMessage(), exception);
        }
    }
}
