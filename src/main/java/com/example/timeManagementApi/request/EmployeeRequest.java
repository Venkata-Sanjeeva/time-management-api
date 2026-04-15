package com.example.timeManagementApi.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeRequest {
	private String name;
	private String designation;
}
