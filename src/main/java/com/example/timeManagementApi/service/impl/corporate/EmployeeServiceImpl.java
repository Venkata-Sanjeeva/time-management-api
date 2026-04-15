package com.example.timeManagementApi.service.impl.corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.Employee;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.request.EmployeeRequest;
import com.example.timeManagementApi.service.interfaces.corporate.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public Object saveEmployeeInDB(EmployeeRequest empReq) {
		Employee emp = new Employee();
		
		emp.setName(empReq.getName());
		emp.setDesignation(empReq.getDesignation());
		
		return empRepo.save(emp);
	}

	@Override
	public Object updateEmployee(String empId, EmployeeRequest empReq) {
		Employee emp = empRepo.findById(empId).orElseThrow();
		
		emp.setName(empReq.getName());
		emp.setDesignation(empReq.getDesignation());
		
		return empRepo.save(emp);
	}

	@Override
	public Object readEmployee(String empId) {
		return empRepo.findById(empId).orElseThrow();
	}

	@Override
	public void deleteEmployee(String empId) {
		empRepo.deleteById(empId);
	}
	
}
