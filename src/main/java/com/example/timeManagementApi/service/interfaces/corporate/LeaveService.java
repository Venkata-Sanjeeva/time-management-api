package com.example.timeManagementApi.service.interfaces.corporate;

import java.util.List;

import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.corporate.LeaveResponse;

public interface LeaveService {
	LeaveResponse saveSingleLeaveInDB(EmployeeLeaveRequest empLeaveReq);
	
	List<LeaveResponse> saveMultipleLeaveInDB(List<EmployeeLeaveRequest> empLeaveReqList);
	
	LeaveResponse updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq);
	
	LeaveResponse readLeave(String leaveId);
	
	List<LeaveResponse> getLeavesByMonth(String empId, Integer year, Integer month);
	
	List<LeaveResponse> readAllLeavesByEmpId(String empId);
	
	void deleteLeave(String leaveId);
	
	void deleteLeavesOfEmployeeByMonth(String empId, Integer year, Integer month);
}
