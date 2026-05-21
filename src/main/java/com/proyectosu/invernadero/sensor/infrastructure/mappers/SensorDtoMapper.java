package com.proyectosu.invernadero.sensor.infrastructure.mappers;

import com.proyectosu.invernadero.sensor.domain.model.SensorData;
import com.proyectosu.invernadero.sensor.dto.response.SensorDataResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SensorDtoMapper {

    SensorDataResponse toResponse(SensorData sensorData);

    List<SensorDataResponse> toResponseList(List<SensorData> sensorDataList);
}
