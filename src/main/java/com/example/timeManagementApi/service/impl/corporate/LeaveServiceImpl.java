package com.example.timeManagementApi.service.impl.corporate;

import java.time.LocalDate;
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
		// Make sure to not save duplicate leave date of the employee
		if(!leaveRepo.existsByEmployeeIdAndLeaveDate(empLeaveReq.getEmpId(), empLeaveReq.getLeaveDate())) {
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
		
		return null;
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
	public List<LeaveResponse> getLeavesByMonth(String empId, Integer year, Integer month) {
		// Create the first day of the month: e.g., 2026-04-01
        LocalDate startDate = LocalDate.of(year, month, 1);
        
        // Create the last day of the month: e.g., 2026-04-30
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        
        List<Leave> leavesRelatedToMonth = leaveRepo.findByEmployee_IdAndLeaveDateBetween(empId, startDate, endDate);
        
        return leavesRelatedToMonth.stream().map((leave) -> LeaveResponse.builder()
        		.empId(empId)
        		.leaveId(leave.getId())
        		.leaveDate(leave.getLeaveDate())
        		.build())
        .toList();
	}
	
	@Override
	public List<LeaveResponse> readAllLeavesByEmpId(String empId) {
		List<Leave> leavesList = leaveRepo.findByEmployeeIdOrderByLeaveDateDesc(empId);
		
		return leavesList.stream()
				.map((leave) -> LeaveResponse.builder()
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
