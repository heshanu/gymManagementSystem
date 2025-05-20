package com.example.userservice.service;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserResponse;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    List<UserResponse> allUsers();
    UserResponse getUserById(String userId);
    UserResponse updateUser(RegisterRequest registerRequest) throws Exception;

    UserResponse deleteUser(String userId) throws Exception;

    UserResponse getUserByEmail(String email);
    Boolean exitByUserId(String userId);

}
