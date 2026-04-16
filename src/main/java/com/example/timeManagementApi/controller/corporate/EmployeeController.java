package com.example.timeManagementApi.controller.corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timeManagementApi.request.corporate.EmployeeRequest;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.service.impl.corporate.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl empService;
	
	@PostMapping("/create")
	public GlobalResponse<?> createEmployee(@RequestBody EmployeeRequest empReq) {
		return GlobalResponse.builder()
				.status(HttpStatus.CREATED.value())
				.data(empService.saveEmployeeInDB(empReq))
				.message("Employee created successfully!")
				.build();
	}
	
	@GetMapping("/read/{id}")
	public GlobalResponse<?> readEmployee(@PathVariable(name = "id") String empId) {
		return GlobalResponse.builder()
				.status(HttpStatus.OK.value())
				.data(empService.readEmployee(empId))
				.message("Employee fetched successfully!")
				.build();
	}
	
	
	@PatchMapping("/update/{id}")
	public GlobalResponse<?> updateEmployee(
			@PathVariable(name = "id") String empId,
			@RequestBody EmployeeRequest empReq) {
		return GlobalResponse.builder()
				.status(HttpStatus.OK.value())
				.data(empService.updateEmployee(empId, empReq))
				.message("Employee updated successfully!")
				.build();
	}
		
	@DeleteMapping("/delete/{id}")
	public GlobalResponse<?> deleteEmployee(@PathVariable(name = "id") String empId) {
		empService.deleteEmployee(empId);
		
		return GlobalResponse.builder()
				.status(HttpStatus.OK.value())
				.data(null)
				.message("Employee deleted successfully!")
				.build();
	}
}
