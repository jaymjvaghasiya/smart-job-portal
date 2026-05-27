package com.jobportal.job_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private Integer status;
    private LocalDateTime timeStamp;

    public ErrorResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
        timeStamp = LocalDateTime.now();
    }
}
