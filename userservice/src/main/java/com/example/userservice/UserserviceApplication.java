package com.example.userservice;

import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(UserEntity.class, UserResponse.class)
				.addMapping(UserEntity::getCreatedAt, UserResponse::setCreateAt)
				.addMapping(UserEntity::getUpdatedAt, UserResponse::setUpdateAt);

		return modelMapper;
	}
}