package com.example.timeManagementApi.service.interfaces.corporate;


import com.example.timeManagementApi.request.corporate.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.EmployeeResponse;

public interface EmployeeService {
	
	EmployeeResponse saveEmployeeInDB(EmployeeRequest empReq);
	
	EmployeeResponse updateEmployee(String empId, EmployeeRequest empReq);
	
	EmployeeResponse readEmployee(String empId);
	
	void deleteEmployee(String empId);
}
