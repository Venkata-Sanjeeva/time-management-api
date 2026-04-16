package com.example.timeManagementApi.controller.corporate;

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

import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.LeaveResponse;
import com.example.timeManagementApi.service.impl.corporate.LeaveServiceImpl;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	@PostMapping("/create/single")
	public ResponseEntity<GlobalResponse<LeaveResponse>> createSingleLeave(@RequestBody EmployeeLeaveRequest leaveReq) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<LeaveResponse>builder()
				.status(HttpStatus.CREATED.value())
				.data(leaveService.saveSingleLeaveInDB(leaveReq))
				.message("Employee Leave created successfully!")
				.build());
	}
	
	@PostMapping("/create/multiple")
	public ResponseEntity<GlobalResponse<List<LeaveResponse>>> createMultipleLeave(@RequestBody List<EmployeeLeaveRequest> leaveReqList) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<List<LeaveResponse>>builder()
				.status(HttpStatus.CREATED.value())
				.data(leaveService.saveMultipleLeaveInDB(leaveReqList))
				.message("Employee Leaves created successfully!")
				.build());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GlobalResponse<LeaveResponse>> readLeave(@PathVariable(name = "id") String leaveId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<LeaveResponse>builder()
				.status(HttpStatus.OK.value())
				.data(leaveService.readLeave(leaveId))
				.message("Employee Leave fetched successfully!")
				.build());
	}
	
	@GetMapping("/read/all/{id}")
	public ResponseEntity<GlobalResponse<List<LeaveResponse>>> readAllLeavesByEmpId(@PathVariable(name = "id") String empId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<List<LeaveResponse>>builder()
				.status(HttpStatus.OK.value())
				.data(leaveService.readAllLeavesByEmpId(empId))
				.message("Employee Leaves fetched successfully!")
				.build());
	}
	
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<GlobalResponse<LeaveResponse>> updateLeave(
			@PathVariable(name = "id") String leaveId,
			@RequestBody EmployeeLeaveRequest leaveReq) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<LeaveResponse>builder()
				.status(HttpStatus.OK.value())
				.data(leaveService.updateLeave(leaveId, leaveReq))
				.message("Employee Leave updated successfully!")
				.build());
	}
		
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GlobalResponse<?>> deleteEmployee(@PathVariable(name = "id") String leaveId) {
		leaveService.deleteLeave(leaveId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.builder()
				.status(HttpStatus.OK.value())
				.data(null)
				.message("Leave deleted successfully!")
				.build());
	}
}
