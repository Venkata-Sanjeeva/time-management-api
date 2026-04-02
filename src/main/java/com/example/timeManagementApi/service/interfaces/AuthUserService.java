package com.example.timeManagementApi.service.interfaces;

import com.example.timeManagementApi.exception.EmailAlreadyExistsException;
import com.example.timeManagementApi.exception.InvalidLoginCredentialsException;
import com.example.timeManagementApi.request.LoginRequest;
import com.example.timeManagementApi.request.RegisterRequest;
import com.example.timeManagementApi.response.LoginResponse;
import com.example.timeManagementApi.response.RegisterResponse;

public interface AuthUserService {
	RegisterResponse register(RegisterRequest request, String role) throws EmailAlreadyExistsException;
	
	LoginResponse login(LoginRequest request) throws InvalidLoginCredentialsException;
}