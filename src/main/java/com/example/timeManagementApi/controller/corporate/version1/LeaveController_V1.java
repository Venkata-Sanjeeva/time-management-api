package com.example.timeManagementApi.controller.corporate.version1;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.timeManagementApi.request.corporate.version0.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.version1.LeaveResponseV1;
import com.example.timeManagementApi.service.impl.corporate.version1.LeaveServiceImpl_V1;

@RestController
@RequestMapping("/leaves/v1")
public class LeaveController_V1 {
	
	@Autowired
	private LeaveServiceImpl_V1 leaveService;
	
	@PostMapping("/create/single")
	public ResponseEntity<GlobalResponse<LeaveResponseV1>> createSingleLeave(@RequestBody EmployeeLeaveRequest leaveReq) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<LeaveResponseV1>builder()
				.status(HttpStatus.CREATED.value())
				.data(leaveService.saveSingleLeaveInDB(leaveReq))
				.message("Employee Leave created successfully!")
				.build());
	}
	
	@PostMapping("/create/multiple")
	public ResponseEntity<GlobalResponse<List<LeaveResponseV1>>> createMultipleLeave(@RequestBody List<EmployeeLeaveRequest> leaveReqList) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<List<LeaveResponseV1>>builder()
				.status(HttpStatus.CREATED.value())
				.data(leaveService.saveMultipleLeaveInDB(leaveReqList))
				.message("Employee Leaves created successfully!")
				.build());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GlobalResponse<LeaveResponseV1>> readLeave(@PathVariable(name = "id") String leaveId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<LeaveResponseV1>builder()
				.status(HttpStatus.OK.value())
				.data(leaveService.readLeave(leaveId))
				.message("Employee Leave fetched successfully!")
				.build());
	}
	
	@GetMapping("/read/all/{id}")
	public ResponseEntity<GlobalResponse<List<LeaveResponseV1>>> readAllLeavesByEmpId(@PathVariable(name = "id") String empId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<List<LeaveResponseV1>>builder()
				.status(HttpStatus.OK.value())
				.data(leaveService.readAllLeavesByEmpId(empId))
				.message("Employee Leaves fetched successfully!")
				.build());
	}
	
	@GetMapping("/month/read/all/{empId}")
	public ResponseEntity<GlobalResponse<List<LeaveResponseV1>>> getEmployeeLeavesByMonth(
	        @PathVariable String empId,
	        @RequestParam int year,
	        @RequestParam int month) {
	    
	    List<LeaveResponseV1> leaves = leaveService.getLeavesByMonth(empId, year, month);
	    
	    return ResponseEntity.status(HttpStatus.OK)
	    		.body(GlobalResponse.<List<LeaveResponseV1>>builder()
	    				.status(HttpStatus.OK.value())
	    				.data(leaves)
	    				.message("Leave Dates Fetched successfully!")
	    				.build());
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<GlobalResponse<LeaveResponseV1>> updateLeave(
			@PathVariable(name = "id") String leaveId,
			@RequestBody EmployeeLeaveRequest leaveReq) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<LeaveResponseV1>builder()
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
