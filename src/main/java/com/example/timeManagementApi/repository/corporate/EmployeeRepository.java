package com.example.timeManagementApi.repository.corporate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
}
