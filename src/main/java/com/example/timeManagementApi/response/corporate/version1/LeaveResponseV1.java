package com.example.timeManagementApi.response.corporate.version1;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveResponseV1 {
	private String leaveId;
	private String empId;
	private LocalDate leaveDate;
}
