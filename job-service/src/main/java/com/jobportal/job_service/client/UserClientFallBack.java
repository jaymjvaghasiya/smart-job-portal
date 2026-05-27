package com.jobportal.job_service.client;

import com.jobportal.job_service.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserClientFallBack implements UserClient {

    @Override
    public UserResponse getUserById(Long id) {
        log.warn("User service is down. Using fallback for user id: {}", id);
        UserResponse fallback = new UserResponse();
        fallback.setId(id);
        fallback.setFirstname("Unknown user");
        fallback.setEmail("");
        return fallback;
    }
}
