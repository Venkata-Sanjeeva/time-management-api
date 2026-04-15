package com.example.timeManagementApi.request;

import java.util.List;

import com.example.timeManagementApi.enums.Shifts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
