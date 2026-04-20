package com.example.timeManagementApi.service.interfaces.corporate.version1;

import java.util.List;

import com.example.timeManagementApi.request.corporate.version0.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.corporate.version1.LeaveResponseV1;

public interface LeaveService_V1 {
	LeaveResponseV1 saveSingleLeaveInDB(EmployeeLeaveRequest empLeaveReq);
	
	List<LeaveResponseV1> saveMultipleLeaveInDB(List<EmployeeLeaveRequest> empLeaveReqList);
	
	LeaveResponseV1 updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq);
	
	LeaveResponseV1 readLeave(String leaveId);
	
	List<LeaveResponseV1> getLeavesByMonth(String empId, Integer year, Integer month);
	
	List<LeaveResponseV1> readAllLeavesByEmpId(String empId);
	
	void deleteLeave(String leaveId);
	
	void deleteLeavesOfEmployeeByMonth(String empId, Integer year, Integer month);
}
