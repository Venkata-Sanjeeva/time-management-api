package com.example.timeManagementApi.request;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeLeaveRequest {
	private UUID empId;
	
	private LocalDate leaveDate;
}
