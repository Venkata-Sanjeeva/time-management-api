package com.example.timeManagementApi.service.impl.corporate.version1;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.entity.corporate.version1.Employee_V1;
import com.example.timeManagementApi.entity.corporate.version1.Roster_V1;
import com.example.timeManagementApi.entity.corporate.version1.Shift_V1;
import com.example.timeManagementApi.exception.UserNotFoundException;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.version1.EmployeeRepository_V1;
import com.example.timeManagementApi.repository.corporate.version1.RosterRepository_V1;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1.ConstraintsDTO;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1.ShiftDTO;
import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.version1.RosterDTOV1;
import com.example.timeManagementApi.response.corporate.version1.RosterResponseV1;
import com.example.timeManagementApi.service.interfaces.corporate.version1.RosterService_V1;

@Service
public class RosterServiceImpl_V1 implements RosterService_V1 {

	@Autowired
	private RosterRepository_V1 rosterRepo;
	
	@Autowired
	private EmployeeRepository_V1 empRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	private RosterResponseV1 convertRosterV1ToResponse(Roster_V1 roster) {
		return RosterResponseV1.builder()
				.rosterId(roster.getId())
				.startDate(roster.getStartDate())
				.endDate(roster.getEndDate())
				.daysToAssignEachEmp(roster.getDaysToAssignEachEmp())
				.includeWeekends(roster.getIncludeWeekends())
				.weekdaysOff(roster.getWeekdaysOff())
				.seniorStaffPresence(roster.getSeniorStaffPresence())
				.empList(roster.getAllocatedEmployees().stream()
						.map((empObj) -> new RosterResponseV1.EmployeeAndLeaveDTOV1(
								empObj.getId(), 
								empObj.getName(), 
								empObj.getEmail(), 
								empObj.getDesignation(), 
								empObj.getLeavesTaken().stream()
								.map((leaveObj) -> new RosterResponseV1.EmployeeAndLeaveDTOV1.LeaveDTOV1(
										leaveObj.getId(), 
										leaveObj.getLeaveDate())
									).toList())
							).toList()
					).build();
	}
	
	@Override
	public RosterResponseV1 saveRosterInDB_V1(String userEmail, RosterRequestVersion1 rosterReq) {

		User user = userRepo.findByEmail(userEmail)
			    .orElseThrow(() -> new UserNotFoundException("User not found: " + userEmail));
		
		Roster_V1 rosterV1 = new Roster_V1();
		
		rosterV1.setUser(user);
		
		List<ShiftDTO> shiftsDTO = rosterReq.getShifts();
		ConstraintsDTO constraintsDTO = rosterReq.getConstraints();

		rosterV1.setStartDate(rosterReq.getStartDate());
		rosterV1.setEndDate(rosterReq.getEndDate());
		rosterV1.setDaysToAssignEachEmp(constraintsDTO.getDaysPerEmployee());
		rosterV1.setIncludeWeekends(constraintsDTO.isIncludeWeekends());
		rosterV1.setSeniorStaffPresence(constraintsDTO.isRequireSeniorOnShift());
		rosterV1.setWeekdaysOff(constraintsDTO.getOffDaysPerRotation());
		
		Set<Employee_V1> empList = rosterReq.getEmployeeIds()
			    .stream()
			    .map((empId) -> {
			        Employee_V1 emp = empRepo.findById(empId)
			            .orElseThrow(() -> new RuntimeException("Employee ID " + empId + " not found in DB"));
			        rosterV1.addEmployee(emp);
			        return emp;
			    }).collect(Collectors.toSet());
		
		rosterV1.setAllocatedEmployees(empList);
		
		List<Shift_V1> shiftsList = new ArrayList<Shift_V1>();
		
		shiftsDTO.forEach(shiftObj -> {
			
			String shiftName = shiftObj.getName();
			Boolean isActive = shiftObj.isActive();
			LocalTime startTime = LocalTime.parse(shiftObj.getStart());
			LocalTime endTime = LocalTime.parse(shiftObj.getEnd());
			
			Shift_V1 shiftV1 = new Shift_V1();
			
			shiftV1.setName(shiftName);
			shiftV1.setStart(startTime);
			shiftV1.setEnd(endTime);
			shiftV1.setActive(isActive);
			
			shiftV1.setRoster(rosterV1);
			
			shiftsList.add(shiftV1);
		});
		
		rosterV1.setShifts(shiftsList);
		
		Roster_V1 savedRoster = rosterRepo.save(rosterV1);
		
		return convertRosterV1ToResponse(savedRoster);
		
	}

	@Override
	public RosterResponseV1 updateRoster(String rosterId, RosterRequestVersion1 rosterReq) {
		return null;
	}

	@Override
	public RosterResponseV1 readRoster(String rosterId) {
		Roster_V1 roster = rosterRepo.findById(rosterId).orElseThrow();
		return convertRosterV1ToResponse(roster);
	}

	@Override
	public RosterChartResponse generateRosterChart(String rosterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RosterDTOV1> readAllRostersRelatesToUserEmail(String userEmail) {
		List<Roster_V1> rosterList = rosterRepo.findAll();
		
		return rosterList.stream().map((roster) -> RosterDTOV1.builder()
				.rosterId(roster.getId())
				.startDate(roster.getStartDate())
				.endDate(roster.getEndDate())
				.build())
			.toList();
	}

	@Override
	public void deleteRoster(String rosterId) {
		rosterRepo.deleteById(rosterId);	
	}
	
}
