package com.proyectosu.invernadero.notifications.inbound.controller;

import com.proyectosu.invernadero.notifications.application.usecase.UpdateNotificationPreferencesUseCase;
import com.proyectosu.invernadero.notifications.dto.request.NotificationPreferencesRequest;
import com.proyectosu.invernadero.notifications.dto.response.NotificationPreferencesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{user_id}/notification-preferences")
@RequiredArgsConstructor
public class NotificationPreferencesController {

    private final UpdateNotificationPreferencesUseCase updateNotificationPreferencesUseCase;

    @PatchMapping
    public ResponseEntity<NotificationPreferencesResponse> update(
            @PathVariable("user_id") String userId,
            @Valid @RequestBody NotificationPreferencesRequest request
    ) {
        updateNotificationPreferencesUseCase.ejecutar(userId, request);

        return ResponseEntity.ok(new NotificationPreferencesResponse(
                "Notification preferences updated successfully"
        ));
    }
}