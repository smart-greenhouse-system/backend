package com.proyectosu.invernadero.notifications.center.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NotificationsListResponse {

    private List<NotificationResponse> notifications;
}