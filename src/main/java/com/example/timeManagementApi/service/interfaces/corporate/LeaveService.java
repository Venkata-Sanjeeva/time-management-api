package com.example.timeManagementApi.service.interfaces.corporate;

import java.util.List;

import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;

public interface LeaveService {
	Object saveSingleLeaveInDB(EmployeeLeaveRequest empLeaveReq);
	
	Object saveMultipleLeaveInDB(List<EmployeeLeaveRequest> empLeaveReqList);
	
	Object updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq);
	
	Object readLeave(String leaveId);
	
	void deleteLeave(String leaveId);
}
