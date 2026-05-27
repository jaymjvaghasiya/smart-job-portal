package com.jobportal.job_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String phone;
    private List<String> skill;
}
