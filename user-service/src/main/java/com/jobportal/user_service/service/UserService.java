package com.jobportal.user_service.service;

import com.jobportal.user_service.dto.request.LoginRequest;
import com.jobportal.user_service.dto.request.RegisterRequest;
import com.jobportal.user_service.dto.request.UpdateProfileRequest;
import com.jobportal.user_service.dto.response.AuthResponse;
import com.jobportal.user_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse register(@Valid RegisterRequest request);

    AuthResponse login(@Valid LoginRequest request);

    UserResponse getUserById(Long id);

    UserResponse updateProfile(Long id, @Valid UpdateProfileRequest request);
}
