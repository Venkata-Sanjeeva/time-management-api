package com.example.timeManagementApi.service.interfaces.corporate.version1;


import java.util.List;

import com.example.timeManagementApi.request.corporate.version0.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.version1.EmployeeResponseV1;

public interface EmployeeService_V1 {
	
	EmployeeResponseV1 saveEmployeeInDB(String userEmail, EmployeeRequest empReq);
	
	EmployeeResponseV1 updateEmployee(String empId, EmployeeRequest empReq);
	
	EmployeeResponseV1 readEmployee(String empId);
	
	List<EmployeeResponseV1> readAllEmployeeRelatedToUser(String userEmail);
	
	void deleteEmployee(String empId);
}
