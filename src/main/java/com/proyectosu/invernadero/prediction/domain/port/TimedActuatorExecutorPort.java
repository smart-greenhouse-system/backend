package com.proyectosu.invernadero.prediction.domain.port;

public interface TimedActuatorExecutorPort {
    void execute(String actuatorId, Integer timeActionSeconds);
}
