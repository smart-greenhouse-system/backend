package com.proyectosu.invernadero.notifications.center.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNotificationStatusRequest {

    @NotBlank(message = "status es obligatorio")
    private String status;
}