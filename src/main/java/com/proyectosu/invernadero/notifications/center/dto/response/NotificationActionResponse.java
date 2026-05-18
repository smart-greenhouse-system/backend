package com.proyectosu.invernadero.notifications.center.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationActionResponse {

    private String message;
    private String notification_id;
}