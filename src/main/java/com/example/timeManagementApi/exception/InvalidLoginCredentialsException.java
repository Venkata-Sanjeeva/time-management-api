package com.example.timeManagementApi.exception;

public class InvalidLoginCredentialsException extends RuntimeException {
	public InvalidLoginCredentialsException(String msg) {
		super(msg);
	}
}
