package com.example.timeManagementApi.request.corporate;

import java.util.List;

import com.example.timeManagementApi.enums.Shifts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RosterRequest {
	private String month;
	private String year;
	private Integer daysToAssign;
	private Integer weekdaysOff;
	private Boolean includeWeekends;
	private Boolean seniorStaffPresence;
	private List<String> employeeIds;
	private List<Shifts> shiftsToAssign;
}
