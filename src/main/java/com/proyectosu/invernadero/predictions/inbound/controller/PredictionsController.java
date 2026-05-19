package com.proyectosu.invernadero.predictions.inbound.controller;

import com.proyectosu.invernadero.predictions.application.usecase.ListarPrediccionesUseCase;
import com.proyectosu.invernadero.predictions.dto.response.PrediccionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionsController {

    private final ListarPrediccionesUseCase listarPrediccionesUseCase;

    @GetMapping
    public ResponseEntity<List<PrediccionResponse>> listarPredicciones() {
        List<PrediccionResponse> predictions = listarPrediccionesUseCase.ejecutar()
                .stream()
                .map(PrediccionResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(predictions);
    }
}