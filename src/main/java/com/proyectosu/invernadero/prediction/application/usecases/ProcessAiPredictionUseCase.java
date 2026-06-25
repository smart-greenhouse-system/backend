package com.proyectosu.invernadero.prediction.application.usecases;

import com.proyectosu.invernadero.actuator.domain.model.Actuator;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import com.proyectosu.invernadero.greenhouse.domain.model.GreenhouseConfig;
import com.proyectosu.invernadero.prediction.application.command.ProcessAiPredictionCommand;
import com.proyectosu.invernadero.prediction.domain.model.Prediction;
import com.proyectosu.invernadero.prediction.domain.port.GreenhouseConfigReaderPort;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import com.proyectosu.invernadero.prediction.domain.port.TimedActuatorExecutorPort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ProcessAiPredictionUseCase {
    private static final int MIN_TIME_ACTION_SECONDS = 1;
    private static final int MAX_TIME_ACTION_SECONDS = 3600;

    private final PredictionRepositoryPort predictionRepositoryPort;
    private final GreenhouseConfigReaderPort greenhouseConfigReaderPort;
    private final TimedActuatorExecutorPort timedActuatorExecutorPort;
    private final ActuatorRepositoryPort actuatorRepositoryPort;
    private final DeviceRepositoryPort deviceRepositoryPort;

    public Prediction execute(ProcessAiPredictionCommand command) {
        validateCommand(command);
        validateActuatorAndDevice(command);

        Prediction prediction = Prediction.createFromAiRequest(
                command.getDeviceId(),
                command.getActuatorId(),
                command.getTimeActionSeconds()
        );

        Prediction savedPrediction = predictionRepositoryPort.save(prediction);

        GreenhouseConfig config = greenhouseConfigReaderPort.getCurrentConfig();

        if (!config.isAutomaticMode()) {
            savedPrediction.markAsStoredWithoutExecution(false);
            return predictionRepositoryPort.update(savedPrediction);
        }

        timedActuatorExecutorPort.execute(
                command.getActuatorId(),
                command.getTimeActionSeconds()
        );

        savedPrediction.markAsProcessed(true, true);

        return predictionRepositoryPort.update(savedPrediction);
    }

    private void validateCommand(ProcessAiPredictionCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("La predicción no puede ser nula");
        }

        if (isBlank(command.getDeviceId())) {
            throw new IllegalArgumentException("El deviceId es obligatorio");
        }

        if (isBlank(command.getActuatorId())) {
            throw new IllegalArgumentException("El actuatorId es obligatorio");
        }

        if (command.getTimeActionSeconds() == null) {
            throw new IllegalArgumentException("El timeAction es obligatorio");
        }

        if (command.getTimeActionSeconds() < MIN_TIME_ACTION_SECONDS) {
            throw new IllegalArgumentException("El timeAction debe ser mayor a 0 segundos");
        }

        if (command.getTimeActionSeconds() > MAX_TIME_ACTION_SECONDS) {
            throw new IllegalArgumentException("El timeAction no puede superar 3600 segundos");
        }
    }

    private void validateActuatorAndDevice(ProcessAiPredictionCommand command) {
        Actuator actuator = actuatorRepositoryPort.findByActuatorId(command.getActuatorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Actuador no encontrado: " + command.getActuatorId()
                ));

        if (!actuator.isEnabled()) {
            throw new IllegalArgumentException("Actuador deshabilitado: " + command.getActuatorId());
        }

        deviceRepositoryPort.findByDeviceId(actuator.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dispositivo no encontrado: " + actuator.getDeviceId()
                ));

        if (!isBlank(command.getDeviceId()) && !command.getDeviceId().equals(actuator.getDeviceId())) {
            throw new IllegalArgumentException(
                    "El device_id no coincide con el dispositivo del actuador"
            );
        }

    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
