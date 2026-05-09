package com.proyectosu.invernadero.mqtt.inbound.mapper;

import com.proyectosu.invernadero.mqtt.domain.Mqtt;
import com.proyectosu.invernadero.mqtt.inbound.dto.response.MqttResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MqttResponseMapper {

    MqttResponse toResponse(Mqtt mqtt);

    List<MqttResponse> toResponseList(List<Mqtt> mqttList);
}
