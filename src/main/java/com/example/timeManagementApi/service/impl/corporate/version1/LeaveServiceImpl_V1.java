package com.example.timeManagementApi.service.impl.corporate.version1;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.corporate.version1.Leave_V1;
import com.example.timeManagementApi.repository.corporate.version1.EmployeeRepository_V1;
import com.example.timeManagementApi.repository.corporate.version1.LeaveRepository_V1;
import com.example.timeManagementApi.request.corporate.version0.EmployeeLeaveRequest;
import com.example.timeManagementApi.response.corporate.version1.LeaveResponseV1;
import com.example.timeManagementApi.service.interfaces.corporate.version1.LeaveService_V1;

@Service
public class LeaveServiceImpl_V1 implements LeaveService_V1 {
	
	@Autowired
	private LeaveRepository_V1 leaveRepo;
	
	@Autowired
	private EmployeeRepository_V1 empRepo;

	@Override
	public LeaveResponseV1 saveSingleLeaveInDB(EmployeeLeaveRequest empLeaveReq) {
		if(!leaveRepo.existsByEmployeeIdAndLeaveDate(empLeaveReq.getEmpId(), empLeaveReq.getLeaveDate())) {
			Leave_V1 leave = new Leave_V1();
			
			leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
			leave.setLeaveDate(empLeaveReq.getLeaveDate());
			
			Leave_V1 savedLeave = leaveRepo.save(leave);
			
			return LeaveResponseV1.builder()
					.leaveId(savedLeave.getId())
					.empId(savedLeave.getEmployee().getId())
					.leaveDate(savedLeave.getLeaveDate())
					.build();
		}
		
		return null;
	}

	@Override
	public List<LeaveResponseV1> saveMultipleLeaveInDB(List<EmployeeLeaveRequest> empLeaveReqList) {
		return empLeaveReqList.stream()
				.map((empLeaveReq) -> saveSingleLeaveInDB(empLeaveReq))
				.toList();
	}

	@Override
	public LeaveResponseV1 updateLeave(String leaveId, EmployeeLeaveRequest empLeaveReq) {
		Leave_V1 leave = leaveRepo.findById(leaveId).orElseThrow();
		
		leave.setEmployee(empRepo.findById(empLeaveReq.getEmpId()).orElseThrow());
		leave.setLeaveDate(empLeaveReq.getLeaveDate());
		
		Leave_V1 savedLeave = leaveRepo.save(leave);
		
		return LeaveResponseV1.builder()
				.leaveId(savedLeave.getId())
				.empId(savedLeave.getEmployee().getId())
				.leaveDate(savedLeave.getLeaveDate())
				.build();
	}

	@Override
	public LeaveResponseV1 readLeave(String leaveId) {
		Leave_V1 leave = leaveRepo.findById(leaveId).orElseThrow();
		
		return LeaveResponseV1.builder()
				.leaveId(leave.getId())
				.empId(leave.getEmployee().getId())
				.leaveDate(leave.getLeaveDate())
				.build();
	}

	@Override
	public List<LeaveResponseV1> getLeavesByMonth(String empId, Integer year, Integer month) {
		// Create the first day of the month: e.g., 2026-04-01
        LocalDate startDate = LocalDate.of(year, month, 1);
        
        // Create the last day of the month: e.g., 2026-04-30
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        
        List<Leave_V1> leavesRelatedToMonth = leaveRepo.findByEmployee_IdAndLeaveDateBetween(empId, startDate, endDate);
        
        return leavesRelatedToMonth.stream().map((leave) -> LeaveResponseV1.builder()
        		.empId(empId)
        		.leaveId(leave.getId())
        		.leaveDate(leave.getLeaveDate())
        		.build())
        .toList();
	}

	@Override
	public List<LeaveResponseV1> readAllLeavesByEmpId(String empId) {
		List<Leave_V1> leavesList = leaveRepo.findByEmployeeIdOrderByLeaveDateDesc(empId);
		
		return leavesList.stream()
				.map((leave) -> LeaveResponseV1.builder()
						.leaveId(leave.getId())
						.empId(leave.getEmployee().getId())
						.leaveDate(leave.getLeaveDate())
						.build())
				.toList();
	}

	@Override
	public void deleteLeave(String leaveId) {
		leaveRepo.deleteById(leaveId);
	}

	@Override
	public void deleteLeavesOfEmployeeByMonth(String empId, Integer year, Integer month) {
		// Create the first day of the month: e.g., 2026-04-01
        LocalDate startDate = LocalDate.of(year, month, 1);
        
        // Create the last day of the month: e.g., 2026-04-30
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        
        leaveRepo.deleteByEmployeeAndMonth(empId, startDate, endDate);
	}

	
	
}
