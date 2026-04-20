package com.example.timeManagementApi.repository.corporate.version1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.version1.Employee_V1;

public interface EmployeeRepository_V1 extends JpaRepository<Employee_V1, String>{
	List<Employee_V1> findByUserEmail(String email);
	Employee_V1 findByEmail(String empEmail);
}
