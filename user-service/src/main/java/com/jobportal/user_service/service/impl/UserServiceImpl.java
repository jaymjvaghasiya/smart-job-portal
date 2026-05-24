package com.jobportal.user_service.service.impl;

import com.jobportal.user_service.dto.request.LoginRequest;
import com.jobportal.user_service.dto.request.RegisterRequest;
import com.jobportal.user_service.dto.request.UpdateProfileRequest;
import com.jobportal.user_service.dto.response.AuthResponse;
import com.jobportal.user_service.dto.response.UserResponse;
import com.jobportal.user_service.entity.UserEntity;
import com.jobportal.user_service.exception.EmailAlreadyExistsException;
import com.jobportal.user_service.exception.InvalidCredentialsException;
import com.jobportal.user_service.exception.UserNotFoundException;
import com.jobportal.user_service.repository.UserRepository;
import com.jobportal.user_service.security.JwtUtil;
import com.jobportal.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(RegisterRequest request) {
        log.info("Register new user: " + request.getEmail());

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists : {} " + request.getEmail());
        }

        UserEntity user = new UserEntity();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setSkill(request.getSkill());

        UserEntity saved = userRepository.save(user);
        log.info("User register successfully with id : {}" + saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for : {]", request.getEmail());

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("No account found with : " + request.getEmail()));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect Password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        log.info("Login successful for : {}", user.getEmail());

        return new AuthResponse(token, user.getId(), user.getFirstname(), user.getLastname(), user.getRole());
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateProfile(Long id, UpdateProfileRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        if(request.getFirstname() != null) user.setFirstname(request.getFirstname());
        if(request.getLastname() != null) user.setLastname(request.getLastname());
        if(request.getPhone() != null) user.setPhone(request.getPhone());
        if(request.getSkill() != null) user.setSkill(request.getSkill());

        UserEntity saved = userRepository.save(user);

        return mapToResponse(saved);
    }

    private UserResponse mapToResponse(UserEntity user) {
        return new UserResponse(user.getId(),
                user.getFirstname(), user.getLastname(), user.getEmail(),
                user.getRole(), user.getPhone(), user.getSkill());
    }
}
