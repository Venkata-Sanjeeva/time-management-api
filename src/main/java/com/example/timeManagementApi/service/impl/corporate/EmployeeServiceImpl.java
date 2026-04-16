package com.example.timeManagementApi.service.impl.corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.entity.corporate.Employee;
import com.example.timeManagementApi.exception.UserNotFoundException;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.request.corporate.EmployeeRequest;
import com.example.timeManagementApi.response.corporate.EmployeeResponse;
import com.example.timeManagementApi.service.interfaces.corporate.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public EmployeeResponse saveEmployeeInDB(String userEmail, EmployeeRequest empReq) {
		
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
		
		Employee emp = new Employee();
		
		emp.setName(empReq.getName());
		emp.setDesignation(empReq.getDesignation());
		emp.setEmail(empReq.getEmail());
		emp.setUser(user);
		
		Employee savedEmp = empRepo.save(emp);
		
		return EmployeeResponse.builder()
				.empId(savedEmp.getId())
				.name(savedEmp.getName())
				.email(savedEmp.getEmail())
				.designation(savedEmp.getDesignation())
				.build();
	}

	@Override
	public EmployeeResponse updateEmployee(String empId, EmployeeRequest empReq) {
		Employee emp = empRepo.findById(empId).orElseThrow();
		
		emp.setName(empReq.getName());
		emp.setDesignation(empReq.getDesignation());
		emp.setEmail(empReq.getEmail());
		
		Employee savedEmp = empRepo.save(emp);
		
		return EmployeeResponse.builder()
				.empId(savedEmp.getId())
				.name(savedEmp.getName())
				.email(savedEmp.getEmail())
				.designation(savedEmp.getDesignation())
				.build();
	}

	@Override
	public EmployeeResponse readEmployee(String empId) {
		Employee emp = empRepo.findById(empId).orElseThrow();
		
		return EmployeeResponse.builder()
				.empId(emp.getId())
				.name(emp.getName())
				.email(emp.getEmail())
				.designation(emp.getDesignation())
				.build();
	}

	@Override
	public void deleteEmployee(String empId) {
		empRepo.deleteById(empId);
	}
	
}
