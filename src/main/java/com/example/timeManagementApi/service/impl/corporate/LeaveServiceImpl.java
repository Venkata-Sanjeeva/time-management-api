package com.example.timeManagementApi.service.impl.corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.corporate.Leave;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.LeaveRepository;
import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;
import com.example.timeManagementApi.service.interfaces.corporate.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepository leaveRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public Object saveLeaveInDB(EmployeeLeaveRequest empLeaveReq) {
		Leave leave = new Leave();
		
		leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
		leave.setLeaveDate(empLeaveReq.getLeaveDate());
		
		return leaveRepo.save(leave);
	}

	@Override
	public Object updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq) {
		Leave leave = leaveRepo.findById(leaveId).orElseThrow();
		
		leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
		leave.setLeaveDate(empLeaveReq.getLeaveDate());
		
		return leaveRepo.save(leave);
	}

	@Override
	public Object readLeave(String leaveId) {
		return leaveRepo.findById(leaveId).orElseThrow();
	}

	@Override
	public void deleteLeave(String leaveId) {
		leaveRepo.deleteById(leaveId);
	}

}
