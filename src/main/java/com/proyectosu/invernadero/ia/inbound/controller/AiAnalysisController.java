package com.proyectosu.invernadero.ia.inbound.controller;

import com.proyectosu.invernadero.ia.application.usecase.PredictImageUseCase;
import com.proyectosu.invernadero.ia.dto.AiPredictionRequest;
import com.proyectosu.invernadero.ia.dto.AiPredictionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiAnalysisController {

    private final PredictImageUseCase predictImageUseCase;

    public AiAnalysisController(PredictImageUseCase predictImageUseCase) {
        this.predictImageUseCase = predictImageUseCase;
    }

    @PostMapping("/predict/{model}")
    public ResponseEntity<AiPredictionResponse> predict(
            @PathVariable String model,
            @Valid @RequestBody AiPredictionRequest request
    ) {
        return ResponseEntity.ok(predictImageUseCase.execute(model, request));
    }
}
