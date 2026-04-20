package com.example.timeManagementApi.response.corporate.version0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RosterDTO {
	private String rosterId;
	private String rosterMonth;
	private String rosterYear;
}
