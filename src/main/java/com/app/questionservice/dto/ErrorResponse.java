package com.app.questionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ErrorResponse class to represent a custom error response.
 */
@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String reason;
    private String message;
    private String path;
}