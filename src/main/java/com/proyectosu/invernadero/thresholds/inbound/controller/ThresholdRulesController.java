package com.proyectosu.invernadero.thresholds.inbound.controller;

import com.proyectosu.invernadero.thresholds.application.usecase.SaveThresholdRuleUseCase;
import com.proyectosu.invernadero.thresholds.domain.model.ThresholdRule;
import com.proyectosu.invernadero.thresholds.dto.request.ThresholdRuleRequest;
import com.proyectosu.invernadero.thresholds.dto.response.ThresholdRuleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/threshold-rules")
@RequiredArgsConstructor
public class ThresholdRulesController {

    private final SaveThresholdRuleUseCase saveThresholdRuleUseCase;

    @PostMapping
    public ResponseEntity<ThresholdRuleResponse> save(@Valid @RequestBody ThresholdRuleRequest request) {
        ThresholdRule thresholdRule = saveThresholdRuleUseCase.ejecutar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ThresholdRuleResponse(
                        "Threshold rule created successfully",
                        thresholdRule.getId()
                )
        );
    }
}