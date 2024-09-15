package com.app.questionservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.TypeMismatchException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.app.questionservice.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>();

    static {
        // 4xx Client Error
        EXCEPTION_STATUS_MAP.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);
        EXCEPTION_STATUS_MAP.put(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        EXCEPTION_STATUS_MAP.put(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE);
        EXCEPTION_STATUS_MAP.put(ConstraintViolationException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(DataIntegrityViolationException.class, HttpStatus.CONFLICT);
        EXCEPTION_STATUS_MAP.put(TypeMismatchException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);

        // 5xx Server Error
        EXCEPTION_STATUS_MAP.put(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(IllegalStateException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(UnsupportedOperationException.class, HttpStatus.NOT_IMPLEMENTED);
        EXCEPTION_STATUS_MAP.put(ServiceUnavailableException.class, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest request) {
        return createErrorResponse(request, HttpStatus.NOT_FOUND, "Route not found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        HttpStatus httpStatus = getHttpStatus(ex);
        return createErrorResponse(request, httpStatus, ex.getMessage());
    }

    private HttpStatus getHttpStatus(Exception ex) {
        return EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpServletRequest request, HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .reason(httpStatus.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
