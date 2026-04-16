package com.example.timeManagementApi.service.interfaces.corporate;


import java.util.List;

import com.example.timeManagementApi.request.corporate.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.EmployeeResponse;

public interface EmployeeService {
	
	EmployeeResponse saveEmployeeInDB(String userEmail, EmployeeRequest empReq);
	
	EmployeeResponse updateEmployee(String empId, EmployeeRequest empReq);
	
	EmployeeResponse readEmployee(String empId);
	
	List<EmployeeResponse> readAllEmployeeRelatedToUser(String userEmail);
	
	void deleteEmployee(String empId);
}
