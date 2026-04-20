package com.example.timeManagementApi.request.corporate.version0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
	private String email;
	private String name;
	private String designation;
}
