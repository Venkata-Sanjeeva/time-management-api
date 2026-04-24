package com.example.timeManagementApi.service.impl.corporate.version1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.entity.corporate.version1.Employee_V1;
import com.example.timeManagementApi.entity.corporate.version1.Leave_V1;
import com.example.timeManagementApi.entity.corporate.version1.Roster_V1;
import com.example.timeManagementApi.entity.corporate.version1.SeniorEmployee;
import com.example.timeManagementApi.entity.corporate.version1.Shift_V1;
import com.example.timeManagementApi.exception.UserNotFoundException;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.version1.EmployeeRepository_V1;
import com.example.timeManagementApi.repository.corporate.version1.RosterRepository_V1;
import com.example.timeManagementApi.repository.corporate.version1.SeniorEmployeeRepository;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1.ConstraintsDTO;
import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1.ShiftDTO;
import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse.EmployeeRow;
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
	private SeniorEmployeeRepository seniorEmpRepo;
	
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
		
		Set<SeniorEmployee> seniorEmpList = new HashSet<SeniorEmployee>();
		
		if(constraintsDTO.isRequireSeniorOnShift()) {
			rosterReq.getSeniors().stream()
		    .forEach((empId) -> {
		        Employee_V1 emp = empRepo.findById(empId)
		            .orElseThrow(() -> new RuntimeException("Employee ID " + empId + " not found in DB"));
		        
		        SeniorEmployee seniorEmp = new SeniorEmployee();
		        
		        seniorEmp.setEmployee(emp);
		        seniorEmp.setRoster(rosterV1);
		        
		        seniorEmpList.add(seniorEmp);
		    });
		}
		
		Set<Employee_V1> empList = rosterReq.getEmployeeIds()
			    .stream()
			    .map((empId) -> {
			        Employee_V1 emp = empRepo.findById(empId)
			            .orElseThrow(() -> new RuntimeException("Employee ID " + empId + " not found in DB"));
			        rosterV1.addEmployee(emp);
			        return emp;
			    }).collect(Collectors.toSet());
		
		rosterV1.setAllocatedEmployees(empList);
		rosterV1.setSeniorEmployees(seniorEmpList);
		
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
	    // 1. Fetch Roster and associated data
	    Roster_V1 roster = rosterRepo.findById(rosterId)
	            .orElseThrow(() -> new RuntimeException("Roster not found"));
	    
	    RosterChartResponse chartResponse = new RosterChartResponse();
	    chartResponse.setRosterId(rosterId);

	    // 2. Prepare the date range
	    LocalDate startDate = roster.getStartDate();
	    LocalDate endDate = roster.getEndDate();
	    List<LocalDate> allDates = startDate.datesUntil(endDate.plusDays(1)).toList();
	    chartResponse.setDates(allDates.stream().map(LocalDate::toString).toList());

	    // 3. Prepare Shift Rotation Logic
	    List<Shift_V1> activeShifts = roster.getShifts().stream()
	            .filter(Shift_V1::getActive)
	            .toList();
	    
	    List<EmployeeRow> rows = new ArrayList<>();

	    // 4. Iterate through each allocated employee
	    for (Employee_V1 emp : roster.getAllocatedEmployees()) {
	        EmployeeRow row = new EmployeeRow();
	        row.setEmpName(emp.getName());
	        Map<String, String> dayStatus = new HashMap<>();

	        // Get employee's leave dates in a Set for O(1) lookup
	        Set<LocalDate> leaveDates = emp.getLeavesTaken().stream()
	                .map(Leave_V1::getLeaveDate)
	                .collect(Collectors.toSet());

	        // Tracking index to rotate shifts fairly
	        int shiftIndex = 0;

	        for (LocalDate date : allDates) {
	            String dateStr = date.toString();
	            
	            // PRIORITY 1: Check for Leaves
	            if (leaveDates.contains(date)) {
	                dayStatus.put(dateStr, "LEAVE");
	            } 
	            // PRIORITY 2: Check for Weekends/Offs
	            else if (isOffDay(date, roster)) {
	                dayStatus.put(dateStr, "OFF");
	            } 
	            // PRIORITY 3: Assign Shift
	            else {
	                // Logic: Assign next available shift and increment index
	                if (!activeShifts.isEmpty()) {
	                    Shift_V1 assignedShift = activeShifts.get(shiftIndex % activeShifts.size());
	                    dayStatus.put(dateStr, assignedShift.getName());
	                    shiftIndex++; 
	                } else {
	                    dayStatus.put(dateStr, "NO_SHIFT_DEFINED");
	                }
	            }
	        }
	        row.setDayStatus(dayStatus);
	        row.setIsSenior(seniorEmpRepo.existsByEmployeeId(emp.getId()));
	        rows.add(row);
	    }

	    chartResponse.setRows(rows);
	    return chartResponse;
	}

	// Helper method to determine if a day is a scheduled "Off"
	private boolean isOffDay(LocalDate date, Roster_V1 roster) {
	    DayOfWeek day = date.getDayOfWeek();
	    
	    // If includeWeekends is true, Saturday/Sunday are assigned as OFF
	    if (roster.getIncludeWeekends() && 
	       (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)) {
	        return true;
	    }
	    
	    // Custom Weekday Off Logic (Example: If weekdaysOff is 1, maybe Monday is off)
	    // You can adjust this logic based on how you store 'weekdaysOff' (int vs bitmask)
	    return false; 
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