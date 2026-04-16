package com.example.timeManagementApi.service.interfaces.corporate;

import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;

public interface LeaveService {
	Object saveLeaveInDB(EmployeeLeaveRequest empLeaveReq);
	
	Object updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq);
	
	Object readLeave(String leaveId);
	
	void deleteLeave(String leaveId);
}
