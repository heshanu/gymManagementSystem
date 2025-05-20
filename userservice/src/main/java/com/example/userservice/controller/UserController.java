package com.example.userservice.controller;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {
        UserResponse selectedUser = userService.getUserById(userId);
        return ResponseEntity.ok(selectedUser); // Changed to OK (200) from ACCEPTED (202)
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email){
        UserResponse userResponse=userService.getUserByEmail(email);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUserProfile() {
        List<UserResponse> allUsers = userService.allUsers();
        return ResponseEntity.ok(allUsers); // Changed to OK (200)
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserResponse response = userService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error occurred while registering user: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody RegisterRequest registerRequest){
        try {
            UserResponse response = userService.updateUser(registerRequest);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String userId) throws Exception {
        UserResponse selectedUser = userService.deleteUser(userId);
        return new ResponseEntity<>(selectedUser,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.exitByUserId(userId),HttpStatus.OK);
    }
}