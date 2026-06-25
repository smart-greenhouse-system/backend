package com.proyectosu.invernadero.ai.infrastructure.outbound.http;

import com.proyectosu.invernadero.ai.domain.model.AiImagePredictionResult;
import com.proyectosu.invernadero.ai.domain.port.AiPredictionClientPort;
import com.proyectosu.invernadero.ai.dto.request.AiImagePredictionRequest;
import com.proyectosu.invernadero.ai.dto.response.AiImagePredictionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FlaskAiPredictionClientAdapter implements AiPredictionClientPort {

    private final RestTemplate restTemplate;

    @Value("${ai.base-url}")
    private String aiBaseUrl;

    @Override
    public AiImagePredictionResult predict(String imageBase64) {
        String url = normalizeBaseUrl(aiBaseUrl) + "/api/predict";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AiImagePredictionRequest> requestEntity = new HttpEntity<>(
                new AiImagePredictionRequest(imageBase64),
                headers
        );

        try {
            ResponseEntity<AiImagePredictionResponse> response = restTemplate.postForEntity(
                    url,
                    requestEntity,
                    AiImagePredictionResponse.class
            );

            AiImagePredictionResponse body = response.getBody();
            if (body == null) {
                throw new IllegalStateException("La respuesta de IA está vacía");
            }

            if (!Boolean.TRUE.equals(body.getSuccess())) {
                throw new IllegalStateException("La IA no pudo procesar la imagen");
            }

            return AiImagePredictionResult.restore(
                    body.getCultivo(),
                    Boolean.TRUE.equals(body.getSuccess()),
                    body.getEstadoPlanta(),
                    body.getConfianza(),
                    body.getTiempoCosechaDias()
            );
        } catch (RestClientException exception) {
            throw new IllegalStateException("Error llamando al servicio de IA", exception);
        }
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new IllegalStateException("La URL base de IA no está configurada");
        }

        String normalized = baseUrl.trim();
        if (normalized.endsWith("/")) {
            return normalized.substring(0, normalized.length() - 1);
        }

        return normalized;
    }
}
