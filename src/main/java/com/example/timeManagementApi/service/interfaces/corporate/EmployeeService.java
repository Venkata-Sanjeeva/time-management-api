package com.example.timeManagementApi.service.interfaces.corporate;


import com.example.timeManagementApi.request.EmployeeRequest;

public interface EmployeeService {
	
	Object saveEmployeeInDB(EmployeeRequest empReq);
	
	Object updateEmployee(String empId, EmployeeRequest empReq);
	
	Object readEmployee(String empId);
	
	void deleteEmployee(String empId);
}
