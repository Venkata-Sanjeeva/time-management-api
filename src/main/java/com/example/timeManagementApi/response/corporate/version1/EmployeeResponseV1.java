package com.example.timeManagementApi.response.corporate.version1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseV1 {
	private String empId;
	private String name;
	private String email;
	private String designation;
}
