package com.jobportal.user_service.controller;

import com.jobportal.user_service.dto.request.LoginRequest;
import com.jobportal.user_service.dto.request.RegisterRequest;
import com.jobportal.user_service.dto.request.UpdateProfileRequest;
import com.jobportal.user_service.dto.response.AuthResponse;
import com.jobportal.user_service.dto.response.UserResponse;
import com.jobportal.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>  login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(userService.login(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request, @PathVariable Long id) {
        return ResponseEntity.ok().body(userService.updateProfile(id, request));
    }
}
