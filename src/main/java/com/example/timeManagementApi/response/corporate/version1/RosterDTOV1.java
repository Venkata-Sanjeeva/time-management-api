package com.example.timeManagementApi.response.corporate.version1;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RosterDTOV1 {
	private String rosterId;
	private LocalDate startDate;
	private LocalDate endDate;
}
