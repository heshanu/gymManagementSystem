package com.example.userservice.repository;

import com.example.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,String>{
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findUserByEmail(@Param("email") String email);

}
