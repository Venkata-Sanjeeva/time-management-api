package com.example.timeManagementApi.repository.corporate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	List<Employee> findByUserEmail(String email);
	Employee findByEmail(String empEmail);
}
