package com.app.questionservice.controller;

import com.app.questionservice.util.HealthCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "/api/health")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class HealthController {

    private final DataSource dataSource;

    @GetMapping
    public ResponseEntity<HealthCheckResponse> checkHealth() {
        HealthCheckResponse response = new HealthCheckResponse();

        // Check database connection
        try {
            dataSource.getConnection().close();
            response.setDatabaseConnection(true);
        } catch (SQLException e) {
            log.error("Database connection failed: {}", e.getMessage());
            response.setDatabaseConnection(false);
        }

        // Check internet connectivity
        checkInternetConnectivity(response);

        // Set current UTC time
        ZonedDateTime systemTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        response.setSystemTimestamp(systemTime.format(formatter));

        return ResponseEntity.ok(response);
    }

    private void checkInternetConnectivity(HealthCheckResponse response) {
        try {
            URI uri = new URI("https://www.google.com");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            response.setInternetConnectivity(responseCode == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            log.error("Internet connectivity check failed: {}", e.getMessage());
            response.setInternetConnectivity(false);
        }
    }
}