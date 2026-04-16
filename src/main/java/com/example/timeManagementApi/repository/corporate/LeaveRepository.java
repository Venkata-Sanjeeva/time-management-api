package com.example.timeManagementApi.repository.corporate;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.Leave;

public interface LeaveRepository extends JpaRepository<Leave, String> {
	List<Leave> findByEmployeeId(String empId);
	List<Leave> findByEmployeeIdOrderByLeaveDateDesc(String empId);
	boolean existsByEmployeeIdAndLeaveDate(String empId, LocalDate leaveDate);
}
