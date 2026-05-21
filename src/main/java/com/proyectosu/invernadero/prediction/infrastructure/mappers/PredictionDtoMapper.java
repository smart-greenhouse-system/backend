package com.proyectosu.invernadero.prediction.infrastructure.mappers;

import com.proyectosu.invernadero.prediction.application.command.ProcessAiPredictionCommand;
import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.dto.request.CreatePredictionRequest;
import com.proyectosu.invernadero.prediction.dto.response.CreatePredictionResponse;
import com.proyectosu.invernadero.prediction.dto.response.PredictionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PredictionDtoMapper {

    @Mapping(target = "deviceId", source = "deviceId")
    @Mapping(target = "processed", source = "processed")
    @Mapping(target = "actuatorId", source = "actuatorId")
    @Mapping(target = "timeActionSeconds", expression = "java(parseTimeAction(request.getTimeAction()))")
    ProcessAiPredictionCommand toCommand(CreatePredictionRequest request);

    @Mapping(target = "message", expression = "java(buildCreateMessage(prediction))")
    @Mapping(target = "processed", expression = "java(prediction.isProcessed())")
    @Mapping(target = "automaticMode", expression = "java(prediction.isAutomaticMode())")
    @Mapping(target = "actuatorExecuted", expression = "java(prediction.isExecuted())")
    @Mapping(target = "timeAction", source = "timeAction")
    CreatePredictionResponse toCreateResponse(Prediction prediction);

    @Mapping(target = "processed", expression = "java(prediction.isProcessed())")
    @Mapping(target = "automaticMode", expression = "java(prediction.isAutomaticMode())")
    @Mapping(target = "executed", expression = "java(prediction.isExecuted())")
    PredictionResponse toResponse(Prediction prediction);

    default Integer parseTimeAction(String timeAction) {
        if (timeAction == null || timeAction.trim().isEmpty()) {
            throw new IllegalArgumentException("El timeAction es obligatorio");
        }

        try {
            return Integer.parseInt(timeAction);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("El timeAction debe ser un número válido");
        }
    }

    default String buildCreateMessage(Prediction prediction) {
        if (!prediction.isAutomaticMode()) {
            return "Predicción guardada correctamente. Modo automático desactivado";
        }

        if (prediction.isExecuted()) {
            return "Predicción recibida correctamente";
        }

        return "Predicción guardada correctamente";
    }
}
