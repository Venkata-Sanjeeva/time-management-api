package com.example.timeManagementApi.service.impl;

import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.enums.Roles;
import com.example.timeManagementApi.exception.InvalidLoginCredentialsException;
import com.example.timeManagementApi.request.LoginRequest;
import com.example.timeManagementApi.request.RegisterRequest;
import com.example.timeManagementApi.response.LoginResponse;
import com.example.timeManagementApi.response.RegisterResponse;
import com.example.timeManagementApi.service.interfaces.AuthUserService;
import com.example.timeManagementApi.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
	
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtil;
	
    @Override
	public RegisterResponse register(RegisterRequest request, String role) {
    	
    	Roles finalizedRole =  Roles.USER;
    		
    	if(role.toLowerCase().equals("admin")) {
    		finalizedRole = Roles.ADMIN;
    	} else if(role.toLowerCase().equals("mentor")) {
    		finalizedRole = Roles.MENTOR;
    	}

		User savedUser = userService.registerUser(request.getName(), request.getEmail(), request.getPassword(), finalizedRole);

        String token = jwtUtil.generateTokenUsingEmail(savedUser.getEmail());

        return RegisterResponse.builder()
        		.email(request.getEmail())
        		.token(token)
        		.build();
    }

    @Override
	public LoginResponse login(LoginRequest request) {
	    
	    if (!userService.verifyUser(request.getEmail(), request.getPassword())) {
	        throw new InvalidLoginCredentialsException("Invalid email or password");
	    }
	    
	    User user = userService.getUserByEmail(request.getEmail());

	    String token = jwtUtil.generateTokenUsingEmailAndRole(user.getEmail(), Roles.valueOf(user.getRole()));

	    return LoginResponse.builder()
	    		.userUID(user.getUserUID())
	            .email(user.getEmail())
	            .token(token)
	            .role(user.getRole().toString())
	            .build();
	}
}