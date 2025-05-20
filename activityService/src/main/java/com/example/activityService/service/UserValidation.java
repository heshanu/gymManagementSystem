package com.example.activityService.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service

public class UserValidation {
    public UserValidation(WebClient userServiceWebClient) {
        this.userServiceWebClient = userServiceWebClient;
    }
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        try {
            return userServiceWebClient
                    .get()
                    .uri("/api/v1/users/{userId}/validate", userId) // Changed from localhost
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found: " + userId);
            }
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid Request: " + userId);
            }
            throw new RuntimeException("Failed to validate user: " + userId, e);
        }
    }
}
