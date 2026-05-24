package com.jobportal.user_service.dto.request;

import com.jobportal.user_service.entity.UserEntity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RegisterRequest {

    @NotBlank(message = "Firstname is required.")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Minimum 8 characters are required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "Password must contains uppercase, lowercase, number, and special character")
    private String password;

    @NotNull(message = "Role is required")
    private UserEntity.Role role;

    private String phone;

    private List<String> skill;
}
