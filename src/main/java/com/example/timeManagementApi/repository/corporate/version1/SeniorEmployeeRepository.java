package com.example.timeManagementApi.repository.corporate.version1;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.version1.SeniorEmployee;

public interface SeniorEmployeeRepository extends JpaRepository<SeniorEmployee, String> {
	Set<SeniorEmployee> findByRosterId(String rosterId);
}
