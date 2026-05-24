package com.jobportal.user_service.dto.response;

import com.jobportal.user_service.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long id;
    private String firstname;
    private String lastname;
    private UserEntity.Role role;
}
