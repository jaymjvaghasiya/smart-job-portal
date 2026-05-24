package com.jobportal.user_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateProfileRequest {
    private String firstname;
    private String lastname;
    private String phone;
    private List<String> skill;
}
