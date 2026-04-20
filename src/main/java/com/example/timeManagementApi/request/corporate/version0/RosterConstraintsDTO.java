package com.example.timeManagementApi.request.corporate.version0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RosterConstraintsDTO {
	private Integer consecutiveShiftGapHours;
	private Integer daysPerEmployee;
	private Integer offDaysPerRotation;
	private Boolean includeWeekends;
}
