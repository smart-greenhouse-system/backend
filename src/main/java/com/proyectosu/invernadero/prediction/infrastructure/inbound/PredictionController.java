package com.proyectosu.invernadero.prediction.infrastructure.inbound;

import com.proyectosu.invernadero.prediction.application.usecases.ProcessAiPredictionUseCase;
import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.dto.request.CreatePredictionRequest;
import com.proyectosu.invernadero.prediction.dto.response.CreatePredictionResponse;
import com.proyectosu.invernadero.prediction.infrastructure.mappers.PredictionDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor

public class PredictionController {
    private final ProcessAiPredictionUseCase processAiPredictionUseCase;
    private final PredictionDtoMapper predictionDtoMapper;

    @PostMapping
    public ResponseEntity<CreatePredictionResponse> createPrediction(
            @RequestBody CreatePredictionRequest request
    ) {
        Prediction prediction = processAiPredictionUseCase.execute(predictionDtoMapper.toCommand(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(predictionDtoMapper.toCreateResponse(prediction));
    }


}
