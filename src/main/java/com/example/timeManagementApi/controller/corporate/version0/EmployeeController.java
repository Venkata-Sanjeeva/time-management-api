package com.example.timeManagementApi.controller.corporate.version0;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timeManagementApi.request.corporate.version0.EmployeeRequest;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.version0.EmployeeResponse;
import com.example.timeManagementApi.service.impl.corporate.version0.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl empService;
	
	@PostMapping("/create")
	public ResponseEntity<GlobalResponse<EmployeeResponse>> createEmployee(@RequestBody EmployeeRequest empReq, Principal principal) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<EmployeeResponse>builder()
						.status(HttpStatus.CREATED.value())
						.data(empService.saveEmployeeInDB(principal.getName(), empReq))
						.message("Employee created successfully!")
						.build());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GlobalResponse<EmployeeResponse>> readEmployee(@PathVariable(name = "id") String empId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<EmployeeResponse>builder()
				.status(HttpStatus.OK.value())
				.data(empService.readEmployee(empId))
				.message("Employee fetched successfully!")
				.build());
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<GlobalResponse<List<EmployeeResponse>>> readAllEmployeesRelatesToUser(Principal principal) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<List<EmployeeResponse>>builder()
				.status(HttpStatus.OK.value())
				.data(empService.readAllEmployeeRelatedToUser(principal.getName()))
				.message("Employees fetched successfully!")
				.build());
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<GlobalResponse<EmployeeResponse>> updateEmployee(
			@PathVariable(name = "id") String empId,
			@RequestBody EmployeeRequest empReq) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<EmployeeResponse>builder()
				.status(HttpStatus.OK.value())
				.data(empService.updateEmployee(empId, empReq))
				.message("Employee updated successfully!")
				.build());
	}
		
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GlobalResponse<?>> deleteEmployee(@PathVariable(name = "id") String empId) {
		empService.deleteEmployee(empId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.builder()
				.status(HttpStatus.OK.value())
				.data(null)
				.message("Employee deleted successfully!")
				.build());
	}
}
