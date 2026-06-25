package com.proyectosu.invernadero.greenhouse.infrastructure.inbound;

import com.proyectosu.invernadero.greenhouse.application.usecases.GetGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouse.application.usecases.UpdateGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.greenhouse.dto.request.UpdateGreenhouseConfigRequest;
import com.proyectosu.invernadero.greenhouse.dto.response.GreenhouseConfigResponse;
import com.proyectosu.invernadero.greenhouse.infrastructure.mappers.GreenhouseConfigDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class GreenhouseConfigController {

    private final GetGreenhouseConfigUseCase getGreenhouseConfigUseCase;
    private final UpdateGreenhouseConfigUseCase updateGreenhouseConfigUseCase;
    private final GreenhouseConfigDtoMapper greenhouseConfigDtoMapper;

    @GetMapping
    public ResponseEntity<GreenhouseConfigResponse> getConfig() {
        GreenhouseConfig config = getGreenhouseConfigUseCase.execute();

        return ResponseEntity.ok(greenhouseConfigDtoMapper.toResponse(config));
    }

    @PatchMapping
    public ResponseEntity<GreenhouseConfigResponse> updateConfig(
            @RequestBody UpdateGreenhouseConfigRequest request
    ) {
        GreenhouseConfig updatedConfig = updateGreenhouseConfigUseCase.execute(
                greenhouseConfigDtoMapper.toCommand(request)
        );

        return ResponseEntity.ok(greenhouseConfigDtoMapper.toResponse(updatedConfig));
    }
}
