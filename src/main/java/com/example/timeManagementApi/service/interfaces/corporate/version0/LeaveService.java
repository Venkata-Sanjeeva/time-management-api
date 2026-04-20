package com.example.timeManagementApi.service.interfaces.corporate.version0;

import java.util.List;

import com.example.timeManagementApi.request.corporate.version0.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.corporate.version0.LeaveResponse;

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
