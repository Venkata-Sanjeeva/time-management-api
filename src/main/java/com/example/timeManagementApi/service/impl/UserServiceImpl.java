package com.example.timeManagementApi.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.enums.Roles;
import com.example.timeManagementApi.exception.EmailAlreadyExistsException;
import com.example.timeManagementApi.exception.InvalidLoginCredentialsException;
import com.example.timeManagementApi.exception.UserNotFoundException;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.service.interfaces.UserService;
import com.example.timeManagementApi.util.IdentifierGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}
	
	@Override
	public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with " + email + " not found!"));
    }

	@Override
    public User registerUser(String name, String email, String password, Roles role) {

        if (existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email + " already exists in DB");
        }

        User user = new User();

        user.setUserUID(IdentifierGenerator.generate(role.toString()));
        user.setName(name);
        user.setEmail(email);

        user.setPassword(passwordEncoder.encode(password));

    	user.setRole(role.toString());

        return userRepo.save(user);
    }
    
    @Override
    public boolean verifyUser(String userEmail, String userPassword) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new InvalidLoginCredentialsException("Invalid email or password"));
        return passwordEncoder.matches(userPassword, user.getPassword());
    }

}
