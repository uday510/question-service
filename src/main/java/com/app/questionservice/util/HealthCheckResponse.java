package com.app.questionservice.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthCheckResponse {
    private boolean databaseConnection;
    private boolean internetConnectivity;
    private String systemTimestamp;
}