package com.proyectosu.invernadero.actuator.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ActuatorEventResponse {

    private String id;

    @JsonProperty("device_id")
    private String deviceId;

    private String actuator;

    private String action;

    private Boolean executed;

    private String origin;

    @JsonProperty("event_type")
    private String eventType;

    private String status;

    private String topic;

    @JsonProperty("time_action")
    private Integer timeAction;

    @JsonProperty("created_at")
    private Instant createdAt;
}
