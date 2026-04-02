package com.example.timeManagementApi.service.interfaces;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.enums.Roles;
import com.example.timeManagementApi.exception.EmailAlreadyExistsException;
import com.example.timeManagementApi.exception.InvalidLoginCredentialsException;
import com.example.timeManagementApi.exception.UserNotFoundException;

public interface UserService {
	User getUserByEmail(String email) throws UserNotFoundException;
	
	User registerUser(String name, String email, String password, Roles role) throws EmailAlreadyExistsException;
	
	boolean existsByEmail(String email);
	
	boolean verifyUser(String email, String password) throws InvalidLoginCredentialsException;
}

