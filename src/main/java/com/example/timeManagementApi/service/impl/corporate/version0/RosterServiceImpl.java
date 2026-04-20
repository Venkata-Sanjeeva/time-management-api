package com.example.timeManagementApi.service.impl.corporate.version0;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.entity.corporate.version0.Employee;
import com.example.timeManagementApi.entity.corporate.version0.Roster;
import com.example.timeManagementApi.enums.Shifts;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.version0.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.version0.RosterRepository;
import com.example.timeManagementApi.request.corporate.version0.RosterBaseDTO;
import com.example.timeManagementApi.request.corporate.version0.RosterConstraintsDTO;
import com.example.timeManagementApi.request.corporate.version0.RosterRequest;

import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.version0.RosterDTO;
import com.example.timeManagementApi.response.corporate.version0.RosterResponse;
import com.example.timeManagementApi.response.corporate.version0.RosterResponse.EmployeeAndLeaveDTO;
import com.example.timeManagementApi.response.corporate.version0.RosterResponse.EmployeeAndLeaveDTO.LeaveDTO;
import com.example.timeManagementApi.service.interfaces.corporate.version0.RosterService;

@Service
public class RosterServiceImpl implements RosterService {
	
	@Autowired
	private RosterRepository rosterRepo;
	
	
	@Autowired
	private UserRepository userRepo;
	
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
				.rosterMonth(roster.getRosterMonth())
				.rosterYear(roster.getRosterYear())
				.daysToAssignEachEmp(roster.getDaysToAssignEachEmp())
				.includeWeekends(roster.getIncludeWeekends())
				.weekdaysOff(roster.getWeekdaysOff())
				.seniorStaffPresence(roster.getSeniorStaffPresence())
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
	public RosterResponse saveRosterInDB(String userEmail, RosterRequest rosterReq) {
		User user = userRepo.findByEmail(userEmail).orElseThrow();
		
		Roster roster = new Roster();
		
		roster.setUser(user);
		
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
				.map((empId) -> {
					Employee emp = empRepo.findById(empId).orElseThrow();
					roster.addEmployee(emp);
					return emp;
				})
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
	public List<RosterDTO> readAllRostersRelatesToUserEmail(String userEmail) {
		List<Roster> rostersList = rosterRepo.findByUserEmail(userEmail);
		
		return rostersList.stream()
				.map((roster) -> new RosterDTO(
						roster.getId(), 
						roster.getRosterMonth(), 
						roster.getRosterYear()))
				.toList();
	}

	@Override
	public void deleteRoster(String rosterId) {
		rosterRepo.deleteById(rosterId);
	}

	/**
	 * Generates a structured chart for a specific roster period.
		 * @param rosterId The unique identifier for the roster configuration
		 * @return A DTO containing a list of dates and status rows for each employee
	 */
	@Override
	public RosterChartResponse generateRosterChart(String rosterId) {
	    // 1. Fetch the raw roster data (Year, Month, Employees, Leave data)
	    RosterResponse rosterResponse = readRoster(rosterId);
	    RosterChartResponse dto = new RosterChartResponse();
	    dto.setRosterId(rosterId);

	    // 2. Parse the period into a Java YearMonth object for date calculations
	    YearMonth yearMonth = YearMonth.of(
	        Integer.parseInt(rosterResponse.getRosterYear()), 
	        Month.valueOf(rosterResponse.getRosterMonth().toUpperCase())
	    );
	    
	    // 3. Generate all valid calendar dates for that specific month (1st to 28th/30th/31st)
	    List<LocalDate> allDates = IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
	    		.mapToObj(yearMonth::atDay)
	    		.toList();
	    // Convert the date objects to Strings (ISO format) for the UI headers
	    dto.setDates(allDates.stream().map(LocalDate::toString).toList());

	    // empIndex is used to "stagger" schedules so employees don't have identical rosters
	    int empIndex = 0;
	    List<RosterChartResponse.EmployeeRow> rows = new ArrayList<>();

	    // 4. Iterate through each employee to build their individual schedule row
	    for (EmployeeAndLeaveDTO emp : rosterResponse.getEmpList()) {
	        RosterChartResponse.EmployeeRow row = new RosterChartResponse.EmployeeRow();
	        row.setEmpName(emp.getEmpName());
	        
	        // Map to store: Key = "2023-10-01", Value = "SHIFT", "LEAVE", etc.
	        Map<String, String> statusMap = new LinkedHashMap<>();
	        
	        // Tracking variables for weekly constraints (e.g., max 5 days/week)
	        int shiftsAssignedInCurrentWeek = 0;
	        int currentWeekOfYear = -1;

	        // 5. Convert leave list to a Set for O(1) fast lookup during the loop
	        Set<LocalDate> leaves = emp.getLeavesList().stream()
	            .map(LeaveDTO::getLeaveDate)
	            .collect(Collectors.toSet());

	        // 6. ROTATIONAL OFFSET: A simple formula to vary the starting pattern per employee.
	        // This ensures Employee A, B, and C start their work weeks differently.
	        int rotationOffset = empIndex % 3; 

	        for (LocalDate date : allDates) {
	            // A. Week Tracking: Reset the shift counter whenever the calendar week changes
	            int weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
	            if (weekOfYear != currentWeekOfYear) {
	                currentWeekOfYear = weekOfYear;
	                shiftsAssignedInCurrentWeek = 0; 
	            }

	            boolean isWeekend = (date.getDayOfWeek() == DayOfWeek.SATURDAY || 
	                                 date.getDayOfWeek() == DayOfWeek.SUNDAY);

	            // B. Priority 1: Check for Approved Leaves
	            if (leaves.contains(date)) {
	                statusMap.put(date.toString(), "LEAVE");
	            } 
	            // C. Priority 2: Check if Weekends are disabled globally for this roster
	            else if (!rosterResponse.getIncludeWeekends() && isWeekend) {
	                statusMap.put(date.toString(), "WEEKEND_OFF");
	            } 
	            // D. Priority 3: Assign shifts if the employee hasn't hit their weekly limit
	            else if (shiftsAssignedInCurrentWeek < rosterResponse.getDaysToAssignEachEmp()) {
	                
	                /* * STAGGERING LOGIC:
	                 * This conditional uses the rotationOffset to force a "day off" 
	                 * at different intervals for different employees.
	                 * This prevents the "everyone works Monday" bottleneck.
	                 */
	                if (date.getDayOfMonth() % (rotationOffset + 2) == 0 && shiftsAssignedInCurrentWeek == 0) {
	                     statusMap.put(date.toString(), "OFF");
	                } else {
	                    statusMap.put(date.toString(), "SHIFT");
	                    shiftsAssignedInCurrentWeek++;
	                }
	            } 
	            // E. Priority 4: If weekly limit is hit, mark as OFF
	            else {
	                statusMap.put(date.toString(), "OFF");
	            }
	        }
	        
	        row.setDayStatus(statusMap);
	        rows.add(row);
	        empIndex++; // Move to next employee to change the rotational offset
	    }

	    dto.setRows(rows);
	    return dto;
	}

}
