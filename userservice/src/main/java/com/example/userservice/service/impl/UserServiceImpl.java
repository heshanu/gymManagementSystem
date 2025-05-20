package com.example.userservice.service.impl;

import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepo;
import com.example.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public UserResponse register(RegisterRequest request) {
       UserEntity usr=new UserEntity();
       usr.setFirstName(request.getFirstName());
       usr.setLastName(request.getLastName());
       usr.setEmail(request.getEmail());
       usr.setPassword(request.getPassword());
       userRepo.save(usr);
       return modelMapper.map(usr,UserResponse.class);
    }
    @Override
    public List<UserResponse> allUsers() {
        List<UserEntity> usersList= userRepo.findAll();
        return modelMapper.map(usersList,new TypeToken<List<UserResponse>>(){}.getType());
    }

    @Override
    public UserResponse getUserById(String userId) {
      if(userId!=null){
          Optional<UserEntity> selectedUsr=userRepo.findById(userId);
          return modelMapper.map(selectedUsr,UserResponse.class);
      }
      return null;
    }

    @Override
    public UserResponse updateUser(RegisterRequest registerRequest) throws Exception {
        log.info("Inside the update user method");

        if (registerRequest == null) {
            throw new IllegalArgumentException("Register request cannot be null");
        }

        // 1. Find existing user
        UserEntity existingUser = userRepo.findUserByEmail(registerRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found with email: " + registerRequest.getEmail()));

        log.info("Updating user with ID: {}", existingUser.getId());

        // 2. Update only the allowed fields
        existingUser.setPassword(registerRequest.getPassword()); // Always encode passwords
        existingUser.setFirstName(registerRequest.getFirstName());
        existingUser.setLastName(registerRequest.getLastName());
        existingUser.setUpdatedAt(LocalDateTime.now());

        // 3. Save and return
        UserEntity updatedUser = userRepo.save(existingUser);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    public UserResponse deleteUser(String userId) throws Exception {
        UserEntity selectedUser=userRepo.findById(userId).orElseThrow(()->new Exception("User not found with userId:"+userId));
        userRepo.deleteById(selectedUser.getId());
        return modelMapper.map(selectedUser,UserResponse.class);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<UserEntity> selectedUser=userRepo.findUserByEmail(email);
        return modelMapper.map(selectedUser,UserResponse.class);
    }

    @Override
    public Boolean exitByUserId(String userId) {
        return userRepo.existsById(userId);
    }
}
