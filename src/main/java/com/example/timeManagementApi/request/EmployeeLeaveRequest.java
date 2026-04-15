package com.example.timeManagementApi.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeaveRequest {
	private String empId;
	
	private LocalDate leaveDate;
}
