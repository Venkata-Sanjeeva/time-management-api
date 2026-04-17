package com.example.timeManagementApi.service.impl.corporate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.corporate.Employee;
import com.example.timeManagementApi.entity.corporate.Roster;
import com.example.timeManagementApi.enums.Shifts;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.RosterRepository;
import com.example.timeManagementApi.request.corporate.RosterBaseDTO;
import com.example.timeManagementApi.request.corporate.RosterConstraintsDTO;
import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.response.corporate.RosterResponse;
import com.example.timeManagementApi.service.interfaces.corporate.RosterService;

@Service
public class RosterServiceImpl implements RosterService {
	
	@Autowired
	private RosterRepository rosterRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	private List<Shifts> genereateShifts(Map<String, Boolean> shiftsMap) {
		List<Shifts> shifts = new ArrayList<Shifts>();
		
		shiftsMap.forEach((key, value) -> {
			if(value) 
				shifts.add(Shifts.valueOf(key.toUpperCase()));
		});
		
		return shifts;
	}
	
	private RosterResponse convertRosterToResponse(Roster roster) {
		return RosterResponse.builder()
				.rosterId(roster.getId())
				.empList(roster.getAllocatedEmployees().stream()
						.map((empObj) -> new RosterResponse.EmployeeAndLeaveDTO(
								empObj.getId(), 
								empObj.getName(), 
								empObj.getEmail(), 
								empObj.getDesignation(), 
								empObj.getLeavesTaken().stream()
								.map((leaveObj) -> new RosterResponse.EmployeeAndLeaveDTO.LeaveDTO(
										leaveObj.getId(), 
										leaveObj.getLeaveDate())
									).toList())
							).toList()
					).build();
	}

	@Override
	public RosterResponse saveRosterInDB(RosterRequest rosterReq) {
		Roster roster = new Roster();
		
		RosterBaseDTO baseDTO = rosterReq.getBase();
		RosterConstraintsDTO constraintsDTO = rosterReq.getConstraints();

		roster.setRosterYear(baseDTO.getSelectedYear());
		roster.setRosterMonth(baseDTO.getSelectedMonth());
		roster.setDaysToAssignEachEmp(constraintsDTO.getDaysPerEmployee());
		roster.setIncludeWeekends(constraintsDTO.getIncludeWeekends());
		roster.setSeniorStaffPresence(rosterReq.getRequireSeniorOnShift());
		roster.setWeekdaysOff(constraintsDTO.getOffDaysPerRotation());
		
		List<Employee> empList = baseDTO.getSelectedEmployees()
				.stream()
				.map((empId) -> empRepo.findById(empId).orElseThrow())
				.toList();
		
		roster.setAllocatedEmployees(empList);
		roster.setShifts(genereateShifts(rosterReq.getShifts()));
		
		Roster savedRoster = rosterRepo.save(roster);
		
		
		return convertRosterToResponse(savedRoster);
	}

	@Override
	public RosterResponse updateRoster(String rosterId, RosterRequest rosterReq) {
		Roster roster = rosterRepo.findById(rosterId).orElseThrow();
		
		RosterBaseDTO baseDTO = rosterReq.getBase();
		RosterConstraintsDTO constraintsDTO = rosterReq.getConstraints();
		
		roster.setRosterYear(baseDTO.getSelectedYear());
		roster.setRosterMonth(baseDTO.getSelectedMonth());
		roster.setDaysToAssignEachEmp(constraintsDTO.getDaysPerEmployee());
		roster.setIncludeWeekends(constraintsDTO.getIncludeWeekends());
		roster.setSeniorStaffPresence(rosterReq.getRequireSeniorOnShift());
		roster.setWeekdaysOff(constraintsDTO.getOffDaysPerRotation());
		
		List<Employee> empList = baseDTO.getSelectedEmployees()
				.stream()
				.map((empId) -> empRepo.findById(empId).orElseThrow())
				.toList();
		
		roster.setAllocatedEmployees(empList);
		roster.setShifts(genereateShifts(rosterReq.getShifts()));
		
		Roster savedRoster = rosterRepo.save(roster);
		
		return convertRosterToResponse(savedRoster);
	}

	@Override
	public RosterResponse readRoster(String rosterId) {
		Roster roster = rosterRepo.findById(rosterId).orElseThrow();
		return convertRosterToResponse(roster);
	}

	@Override
	public void deleteRoster(String rosterId) {
		rosterRepo.deleteById(rosterId);
	}

}
