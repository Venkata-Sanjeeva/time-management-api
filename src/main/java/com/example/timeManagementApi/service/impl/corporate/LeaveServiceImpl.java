package com.example.timeManagementApi.service.impl.corporate;

import org.springframework.stereotype.Service;

import com.example.timeManagementApi.request.EmployeeLeaveRequest;
import com.example.timeManagementApi.service.interfaces.corporate.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Override
	public Object saveLeaveInDB(EmployeeLeaveRequest empLeaveReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object readLeave(String leaveId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLeave(String leaveId) {
		// TODO Auto-generated method stub

	}

}
