package com.proyectosu.invernadero.prediction.infrastructure.outbound;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "predictions")
public class MongoPredictionDocument {
    @Id
    private String id;

    private String deviceId;

    private String actuatorId;

    private Integer timeAction;

    private Boolean processed;

    private Boolean automaticMode;

    private Boolean executed;

    private Instant createdAt;
}
