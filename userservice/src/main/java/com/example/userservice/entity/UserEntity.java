package com.example.userservice.entity;

import com.example.userservice.model.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    public UserEntity() {
    }
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role=UserRole.USER;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Fixed field name (was 'createAt')

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
