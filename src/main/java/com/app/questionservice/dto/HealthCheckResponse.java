package com.app.questionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthCheckResponse {
    private boolean databaseConnection;
    private boolean cache;
    @Setter
    private boolean internetConnectivity;

}
