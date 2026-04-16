package com.example.timeManagementApi.service.impl.corporate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.corporate.Leave;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.LeaveRepository;
import com.example.timeManagementApi.request.corporate.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.corporate.LeaveResponse;
import com.example.timeManagementApi.service.interfaces.corporate.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepository leaveRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public LeaveResponse saveSingleLeaveInDB(EmployeeLeaveRequest empLeaveReq) {
		Leave leave = new Leave();
		
		leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
		leave.setLeaveDate(empLeaveReq.getLeaveDate());
		
		Leave savedLeave = leaveRepo.save(leave);
		
		return LeaveResponse.builder()
				.leaveId(savedLeave.getId())
				.empId(savedLeave.getEmployee().getId())
				.leaveDate(savedLeave.getLeaveDate())
				.build();
	}
	
	@Override
	public List<LeaveResponse> saveMultipleLeaveInDB(List<EmployeeLeaveRequest> empLeaveReqList) {
		return empLeaveReqList.stream()
				.map((empLeaveReq) -> saveSingleLeaveInDB(empLeaveReq))
				.toList();
	}

	@Override
	public LeaveResponse updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq) {
		Leave leave = leaveRepo.findById(leaveId).orElseThrow();
		
		leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
		leave.setLeaveDate(empLeaveReq.getLeaveDate());
		
		Leave savedLeave = leaveRepo.save(leave);
		
		return LeaveResponse.builder()
				.leaveId(savedLeave.getId())
				.empId(savedLeave.getEmployee().getId())
				.leaveDate(savedLeave.getLeaveDate())
				.build();
	}

	@Override
	public LeaveResponse readLeave(String leaveId) {
		Leave leave = leaveRepo.findById(leaveId).orElseThrow();
		
		return LeaveResponse.builder()
				.leaveId(leave.getId())
				.empId(leave.getEmployee().getId())
				.leaveDate(leave.getLeaveDate())
				.build();
	}

	@Override
	public void deleteLeave(String leaveId) {
		leaveRepo.deleteById(leaveId);
	}

}
