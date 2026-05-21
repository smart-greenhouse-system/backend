package com.proyectosu.invernadero.shared.config;

import com.proyectosu.invernadero.actuator.application.usecases.CreateActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.DeleteActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.ExecuteActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.ExecuteTimedActuatorUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.GetActuatorEventsUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.GetActuatorsUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.ProcessActuatorStateUseCase;
import com.proyectosu.invernadero.actuator.application.usecases.UpdateActuatorUseCase;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorEventRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorRepositoryPort;
import com.proyectosu.invernadero.actuator.domain.port.ActuatorResolverPort;
import com.proyectosu.invernadero.auth.application.usecase.LoginUserUseCase;
import com.proyectosu.invernadero.auth.application.usecase.RegisterUserUseCase;
import com.proyectosu.invernadero.auth.domain.ports.*;
import com.proyectosu.invernadero.device.application.usecases.GetDevicesUseCase;
import com.proyectosu.invernadero.device.application.usecases.UpsertDeviceStatusUseCase;
import com.proyectosu.invernadero.device.domain.port.DeviceRepositoryPort;
import com.proyectosu.invernadero.inventory.application.usecases.CreateInventoryItemUseCase;
import com.proyectosu.invernadero.inventory.application.usecases.GetInventoryUseCase;
import com.proyectosu.invernadero.inventory.application.usecases.UpdateInventoryItemUseCase;
import com.proyectosu.invernadero.inventory.domain.port.InventoryRepositoryPort;
import com.proyectosu.invernadero.ai.application.usecases.AnalyzeImageUseCase;
import com.proyectosu.invernadero.ai.domain.port.AiPredictionClientPort;
import com.proyectosu.invernadero.image.application.usecases.GetLatestImageUseCase;
import com.proyectosu.invernadero.image.application.usecases.StoreLatestImageUseCase;
import com.proyectosu.invernadero.image.domain.port.ImageStoragePort;
import com.proyectosu.invernadero.greenhouse.application.usecases.GetGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouse.application.usecases.UpdateGreenhouseConfigUseCase;
import com.proyectosu.invernadero.greenhouse.domain.port.GreenhouseConfigRepositoryPort;
import com.proyectosu.invernadero.prediction.application.usecases.ProcessAiPredictionUseCase;
import com.proyectosu.invernadero.prediction.domain.port.GreenhouseConfigReaderPort;
import com.proyectosu.invernadero.prediction.domain.port.PredictionRepositoryPort;
import com.proyectosu.invernadero.prediction.domain.port.TimedActuatorExecutorPort;
import com.proyectosu.invernadero.sensor.application.usecases.GetLatestSensorDataUseCase;
import com.proyectosu.invernadero.sensor.application.usecases.GetSensorHistoryUseCase;
import com.proyectosu.invernadero.sensor.application.usecases.SaveSensorDataUseCase;
import com.proyectosu.invernadero.sensor.domain.port.SensorDataRepositoryPort;
import com.proyectosu.invernadero.shared.domain.ports.MqttPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            RoleRepositoryPort roleRepository
    ) {
        return new RegisterUserUseCase(userRepository, passwordEncoder, roleRepository);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtServicePort jwtService
    ) {
        return new LoginUserUseCase(
                userRepository,
                passwordEncoder,
                jwtService
        );
    }

    @Bean
    public ProcessAiPredictionUseCase processAiPredictionUseCase(
            PredictionRepositoryPort predictionRepositoryPort,
            GreenhouseConfigReaderPort greenhouseConfigReaderPort,
            TimedActuatorExecutorPort timedActuatorExecutorPort,
            ActuatorRepositoryPort actuatorRepositoryPort,
            DeviceRepositoryPort deviceRepositoryPort
    ) {
        return new ProcessAiPredictionUseCase(
                predictionRepositoryPort,
                greenhouseConfigReaderPort,
                timedActuatorExecutorPort,
                actuatorRepositoryPort,
                deviceRepositoryPort
        );
    }

    @Bean
    public AnalyzeImageUseCase analyzeImageUseCase(AiPredictionClientPort aiPredictionClientPort) {
        return new AnalyzeImageUseCase(aiPredictionClientPort);
    }

    @Bean
    public StoreLatestImageUseCase storeLatestImageUseCase(ImageStoragePort imageStoragePort) {
        return new StoreLatestImageUseCase(imageStoragePort);
    }

    @Bean
    public GetLatestImageUseCase getLatestImageUseCase(ImageStoragePort imageStoragePort) {
        return new GetLatestImageUseCase(imageStoragePort);
    }

    @Bean
    public GetActuatorsUseCase getActuatorsUseCase(ActuatorRepositoryPort actuatorRepositoryPort) {
        return new GetActuatorsUseCase(actuatorRepositoryPort);
    }

    @Bean
    public CreateActuatorUseCase createActuatorUseCase(ActuatorRepositoryPort actuatorRepositoryPort) {
        return new CreateActuatorUseCase(actuatorRepositoryPort);
    }

    @Bean
    public UpdateActuatorUseCase updateActuatorUseCase(ActuatorRepositoryPort actuatorRepositoryPort) {
        return new UpdateActuatorUseCase(actuatorRepositoryPort);
    }

    @Bean
    public DeleteActuatorUseCase deleteActuatorUseCase(ActuatorRepositoryPort actuatorRepositoryPort) {
        return new DeleteActuatorUseCase(actuatorRepositoryPort);
    }

    @Bean
    public ProcessActuatorStateUseCase processActuatorStateUseCase(
            ActuatorRepositoryPort actuatorRepositoryPort,
            ActuatorEventRepositoryPort actuatorEventRepositoryPort
    ) {
        return new ProcessActuatorStateUseCase(actuatorRepositoryPort, actuatorEventRepositoryPort);
    }

    @Bean
    public GetActuatorEventsUseCase getActuatorEventsUseCase(
            ActuatorEventRepositoryPort actuatorEventRepositoryPort
    ) {
        return new GetActuatorEventsUseCase(actuatorEventRepositoryPort);
    }

    @Bean
    public ExecuteActuatorUseCase executeActuatorUseCase(
            ActuatorEventRepositoryPort actuatorEventRepositoryPort,
            MqttPublisherPort mqttPublisherPort,
            ActuatorRepositoryPort actuatorRepositoryPort,
            DeviceRepositoryPort deviceRepositoryPort
    ) {
        return new ExecuteActuatorUseCase(
                actuatorEventRepositoryPort,
                mqttPublisherPort,
                actuatorRepositoryPort,
                deviceRepositoryPort
        );
    }

    @Bean
    public TimedActuatorExecutorPort timedActuatorExecutorPort(
            ActuatorResolverPort actuatorResolverPort,
            ActuatorEventRepositoryPort actuatorEventRepositoryPort,
            MqttPublisherPort mqttPublisherPort,
            TaskScheduler taskScheduler
    ) {
        return new ExecuteTimedActuatorUseCase(
                actuatorResolverPort,
                actuatorEventRepositoryPort,
                mqttPublisherPort,
                taskScheduler
        );
    }

    @Bean
    public GetGreenhouseConfigUseCase getGreenhouseConfigUseCase(
            GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort
    ) {
        return new GetGreenhouseConfigUseCase(greenhouseConfigRepositoryPort);
    }

    @Bean
    public UpdateGreenhouseConfigUseCase updateGreenhouseConfigUseCase(
            GreenhouseConfigRepositoryPort greenhouseConfigRepositoryPort
    ) {
        return new UpdateGreenhouseConfigUseCase(greenhouseConfigRepositoryPort);
    }

    @Bean
    public GetDevicesUseCase getDevicesUseCase(DeviceRepositoryPort deviceRepositoryPort) {
        return new GetDevicesUseCase(deviceRepositoryPort);
    }

    @Bean
    public UpsertDeviceStatusUseCase upsertDeviceStatusUseCase(DeviceRepositoryPort deviceRepositoryPort) {
        return new UpsertDeviceStatusUseCase(deviceRepositoryPort);
    }

    @Bean
    public SaveSensorDataUseCase saveSensorDataUseCase(
            SensorDataRepositoryPort sensorDataRepositoryPort,
            UpsertDeviceStatusUseCase upsertDeviceStatusUseCase
    ) {
        return new SaveSensorDataUseCase(sensorDataRepositoryPort, upsertDeviceStatusUseCase);
    }

    @Bean
    public GetInventoryUseCase getInventoryUseCase(InventoryRepositoryPort inventoryRepositoryPort) {
        return new GetInventoryUseCase(inventoryRepositoryPort);
    }

    @Bean
    public CreateInventoryItemUseCase createInventoryItemUseCase(
            InventoryRepositoryPort inventoryRepositoryPort
    ) {
        return new CreateInventoryItemUseCase(inventoryRepositoryPort);
    }

    @Bean
    public UpdateInventoryItemUseCase updateInventoryItemUseCase(
            InventoryRepositoryPort inventoryRepositoryPort
    ) {
        return new UpdateInventoryItemUseCase(inventoryRepositoryPort);
    }

    @Bean
    public GetLatestSensorDataUseCase getLatestSensorDataUseCase(
            SensorDataRepositoryPort sensorDataRepositoryPort
    ) {
        return new GetLatestSensorDataUseCase(sensorDataRepositoryPort);
    }

    @Bean
    public GetSensorHistoryUseCase getSensorHistoryUseCase(
            SensorDataRepositoryPort sensorDataRepositoryPort
    ) {
        return new GetSensorHistoryUseCase(sensorDataRepositoryPort);
    }

}
