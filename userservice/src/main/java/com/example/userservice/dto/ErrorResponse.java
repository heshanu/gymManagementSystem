package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, String> errors;
    private int status;


}