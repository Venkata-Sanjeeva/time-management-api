package com.example.timeManagementApi.service.interfaces.corporate.version0;


import java.util.List;

import com.example.timeManagementApi.request.corporate.version0.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.version0.EmployeeResponse;

public interface EmployeeService {
	
	EmployeeResponse saveEmployeeInDB(String userEmail, EmployeeRequest empReq);
	
	EmployeeResponse updateEmployee(String empId, EmployeeRequest empReq);
	
	EmployeeResponse readEmployee(String empId);
	
	List<EmployeeResponse> readAllEmployeeRelatedToUser(String userEmail);
	
	void deleteEmployee(String empId);
}
