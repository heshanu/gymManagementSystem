package com.example.userservice.dto;
import com.example.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role=UserRole.USER;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
