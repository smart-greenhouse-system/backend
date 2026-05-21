package com.proyectosu.invernadero.ai.domain.port;

import com.proyectosu.invernadero.ai.domain.model.AiImagePredictionResult;

public interface AiPredictionClientPort {

    AiImagePredictionResult predict(String imageBase64);
}
