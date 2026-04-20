package com.example.timeManagementApi.repository.corporate.version0;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.version0.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	List<Employee> findByUserEmail(String email);
	Employee findByEmail(String empEmail);
}
