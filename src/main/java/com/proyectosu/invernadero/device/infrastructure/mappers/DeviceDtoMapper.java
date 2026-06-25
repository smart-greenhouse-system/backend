package com.proyectosu.invernadero.device.infrastructure.mappers;

import com.proyectosu.invernadero.device.domain.model.Device;
import com.proyectosu.invernadero.device.dto.response.DeviceResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceDtoMapper {

    DeviceResponse toResponse(Device device);

    List<DeviceResponse> toResponseList(List<Device> devices);
}
