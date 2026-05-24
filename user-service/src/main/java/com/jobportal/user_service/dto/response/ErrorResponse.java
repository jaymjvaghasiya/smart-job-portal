package com.jobportal.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Integer status;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
        timestamp = LocalDateTime.now();
    }
}
