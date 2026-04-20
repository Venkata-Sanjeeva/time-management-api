package com.example.timeManagementApi.service.impl.corporate.version1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.entity.corporate.version1.Employee_V1;
import com.example.timeManagementApi.exception.UserNotFoundException;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.version1.EmployeeRepository_V1;
import com.example.timeManagementApi.request.corporate.version0.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.version1.EmployeeResponseV1;
import com.example.timeManagementApi.service.interfaces.corporate.version1.EmployeeService_V1;

@Service
public class EmployeeServiceImpl_V1 implements EmployeeService_V1 {

	@Autowired
	private EmployeeRepository_V1 empRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public EmployeeResponseV1 saveEmployeeInDB(String userEmail, EmployeeRequest empReq) {
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
		
		Employee_V1 emp = new Employee_V1();
		
		emp.setName(empReq.getName());
		emp.setDesignation(empReq.getDesignation());
		emp.setEmail(empReq.getEmail());
		emp.setUser(user);
		
		Employee_V1 savedEmp = empRepo.save(emp);
		
		return EmployeeResponseV1.builder()
				.empId(savedEmp.getId())
				.name(savedEmp.getName())
				.email(savedEmp.getEmail())
				.designation(savedEmp.getDesignation())
				.build();
	}

	@Override
	public EmployeeResponseV1 updateEmployee(String empId, EmployeeRequest empReq) {
		Employee_V1 emp = empRepo.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empId));
		
		String name = empReq.getName();
		String designation = empReq.getDesignation();
		String email = empReq.getEmail();
		
		if(name != null) emp.setName(name);
		
		if(designation != null) emp.setDesignation(designation);
		
		if(email != null) emp.setEmail(email);
		
		Employee_V1 savedEmp = empRepo.save(emp);
		
		return EmployeeResponseV1.builder()
				.empId(savedEmp.getId())
				.name(savedEmp.getName())
				.email(savedEmp.getEmail())
				.designation(savedEmp.getDesignation())
				.build();
	}

	@Override
	public EmployeeResponseV1 readEmployee(String empId) {
		Employee_V1 emp = empRepo.findById(empId).orElseThrow();
		
		return EmployeeResponseV1.builder()
				.empId(emp.getId())
				.name(emp.getName())
				.email(emp.getEmail())
				.designation(emp.getDesignation())
				.build();
	}

	@Override
	public List<EmployeeResponseV1> readAllEmployeeRelatedToUser(String userEmail) {
		List<Employee_V1> empList = empRepo.findByUserEmail(userEmail);
		
		return empList.stream().map((emp) -> EmployeeResponseV1.builder()
				.empId(emp.getId())
				.name(emp.getName())
				.email(emp.getEmail())
				.designation(emp.getDesignation())
				.build())
			.toList();
	}

	@Override
	public void deleteEmployee(String empId) {
		empRepo.deleteById(empId);
	}

	
}
