package com.example.timeManagementApi.response.corporate;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RosterResponse {
	private String rosterId;
	
	@Data
	@AllArgsConstructor
	public static class EmployeeAndLeaveDTO {
		private String empId;
		private String empName;
		private String empEmail;
		private String empDesignation;
		
		@Data
		@AllArgsConstructor
		public static class LeaveDTO {
			private String leaveId;
			private LocalDate leaveDate;
		}
		private List<LeaveDTO> leavesList;
	}
	
	private List<EmployeeAndLeaveDTO> empList;
}
