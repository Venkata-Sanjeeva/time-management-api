package com.example.timeManagementApi.service.impl.corporate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.corporate.Employee;
import com.example.timeManagementApi.entity.corporate.Roster;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.RosterRepository;
import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.service.interfaces.corporate.RosterService;

@Service
public class RosterServiceImpl implements RosterService {
	
	@Autowired
	private RosterRepository rosterRepo;
	
	@Autowired
	private EmployeeRepository empRepo;

	@Override
	public Object saveRosterInDB(RosterRequest rosterReq) {
		Roster roster = new Roster();
		
		roster.setRosterYear(rosterReq.getYear());
		roster.setRosterMonth(rosterReq.getMonth());
		roster.setDaysToAssignEachEmp(rosterReq.getDaysToAssign());
		roster.setIncludeWeekends(rosterReq.getIncludeWeekends());
		roster.setSeniorStaffPresence(rosterReq.getSeniorStaffPresence());
		roster.setWeekdaysOff(rosterReq.getWeekdaysOff());
		
		List<Employee> empList = rosterReq.getEmployeeIds()
				.stream()
				.map((empId) -> empRepo.findById(empId).orElseThrow())
				.toList();
		
		roster.setAllocatedEmployees(empList);
		roster.setShifts(rosterReq.getShiftsToAssign());
		
		return rosterRepo.save(roster);
	}

	@Override
	public Object updateRoster(String rosterId, RosterRequest rosterReq) {
		Roster roster = rosterRepo.findById(rosterId).orElseThrow();
		
		roster.setRosterYear(rosterReq.getYear());
		roster.setRosterMonth(rosterReq.getMonth());
		roster.setDaysToAssignEachEmp(rosterReq.getDaysToAssign());
		roster.setIncludeWeekends(rosterReq.getIncludeWeekends());
		roster.setSeniorStaffPresence(rosterReq.getSeniorStaffPresence());
		roster.setWeekdaysOff(rosterReq.getWeekdaysOff());
		
		List<Employee> empList = rosterReq.getEmployeeIds()
				.stream()
				.map((empId) -> empRepo.findById(empId).orElseThrow())
				.toList();
		
		roster.setAllocatedEmployees(empList);
		roster.setShifts(rosterReq.getShiftsToAssign());
		
		return rosterRepo.save(roster);
	}

	@Override
	public Object readRoster(String rosterId) {
		return rosterRepo.findById(rosterId).orElseThrow();
	}

	@Override
	public void deleteRoster(String rosterId) {
		rosterRepo.deleteById(rosterId);
	}

}
