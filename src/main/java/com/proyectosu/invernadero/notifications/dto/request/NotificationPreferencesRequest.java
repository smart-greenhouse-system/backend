package com.proyectosu.invernadero.notifications.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationPreferencesRequest {

    @Valid
    @NotNull(message = "events es obligatorio")
    private NotificationEventsRequest events;

    @Valid
    @NotNull(message = "channels es obligatorio")
    private NotificationChannelsRequest channels;

    @Valid
    @NotNull(message = "do_not_disturb es obligatorio")
    private DoNotDisturbRequest do_not_disturb;

    @Getter
    @Setter
    public static class NotificationEventsRequest {

        @NotNull(message = "critical_alerts es obligatorio")
        private Boolean critical_alerts;

        @NotNull(message = "warnings es obligatorio")
        private Boolean warnings;

        @NotNull(message = "offline_sensors es obligatorio")
        private Boolean offline_sensors;

        @NotNull(message = "actuator_failures es obligatorio")
        private Boolean actuator_failures;
    }

    @Getter
    @Setter
    public static class NotificationChannelsRequest {

        @NotNull(message = "push es obligatorio")
        private Boolean push;

        @NotNull(message = "email es obligatorio")
        private Boolean email;

        @NotNull(message = "in_app es obligatorio")
        private Boolean in_app;
    }

    @Getter
    @Setter
    public static class DoNotDisturbRequest {

        @NotNull(message = "enabled es obligatorio")
        private Boolean enabled;

        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "start_time debe tener formato HH:mm")
        private String start_time;

        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "end_time debe tener formato HH:mm")
        private String end_time;
    }
}