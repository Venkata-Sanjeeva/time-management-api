package com.example.timeManagementApi.response.corporate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
	private String empId;
	private String name;
	private String email;
	private String designation;
}
