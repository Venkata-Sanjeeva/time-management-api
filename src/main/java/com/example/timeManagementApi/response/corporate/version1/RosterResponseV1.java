package com.example.timeManagementApi.response.corporate.version1;

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
public class RosterResponseV1 {
	private String rosterId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer daysToAssignEachEmp;
	private Integer weekdaysOff;
	private Boolean includeWeekends;
	private Boolean seniorStaffPresence;
	
	@Data
	@AllArgsConstructor
	public static class EmployeeAndLeaveDTOV1 {
		private String empId;
		private String empName;
		private String empEmail;
		private String empDesignation;
		
		@Data
		@AllArgsConstructor
		public static class LeaveDTOV1 {
			private String leaveId;
			private LocalDate leaveDate;
		}
		private List<LeaveDTOV1> leavesList;
	}
	
	private List<EmployeeAndLeaveDTOV1> empList;
}

