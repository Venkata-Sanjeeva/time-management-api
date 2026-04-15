package com.example.timeManagementApi.request;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeLeaveRequest {
	private String empId;
	
	private LocalDate leaveDate;
}
