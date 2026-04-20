package com.example.timeManagementApi.request.corporate.version0;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RosterBaseDTO {
	private String selectedMonth;
	private String selectedYear;
	private List<String> selectedEmployees;
}
