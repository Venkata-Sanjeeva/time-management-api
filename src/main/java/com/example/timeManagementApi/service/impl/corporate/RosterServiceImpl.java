package com.example.timeManagementApi.service.impl.corporate;

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
import com.example.timeManagementApi.entity.corporate.Employee;
import com.example.timeManagementApi.entity.corporate.Roster;
import com.example.timeManagementApi.enums.Shifts;
import com.example.timeManagementApi.repository.UserRepository;
import com.example.timeManagementApi.repository.corporate.EmployeeRepository;
import com.example.timeManagementApi.repository.corporate.RosterRepository;
import com.example.timeManagementApi.request.corporate.RosterBaseDTO;
import com.example.timeManagementApi.request.corporate.RosterConstraintsDTO;
import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.response.corporate.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.RosterDTO;
import com.example.timeManagementApi.response.corporate.RosterResponse;
import com.example.timeManagementApi.response.corporate.RosterResponse.EmployeeAndLeaveDTO;
import com.example.timeManagementApi.response.corporate.RosterResponse.EmployeeAndLeaveDTO.LeaveDTO;
import com.example.timeManagementApi.service.interfaces.corporate.RosterService;

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

	@Override
	public RosterChartResponse generateRosterChart(String rosterId) {
	    RosterResponse rosterResponse = readRoster(rosterId);
	    RosterChartResponse dto = new RosterChartResponse();
	    dto.setRosterId(rosterId);
	
	    YearMonth yearMonth = YearMonth.of(
	        Integer.parseInt(rosterResponse.getRosterYear()), 
	        Month.valueOf(rosterResponse.getRosterMonth().toUpperCase())
	    );
	    
	    List<LocalDate> allDates = IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
	        .mapToObj(yearMonth::atDay)
	        .collect(Collectors.toList());
	    
	    dto.setDates(allDates.stream().map(LocalDate::toString).collect(Collectors.toList()));
	
	    // Rotational logic: Use the employee's index to stagger their start days
	    int empIndex = 0;
	    List<RosterChartResponse.EmployeeRow> rows = new ArrayList<>();
	
	    for (EmployeeAndLeaveDTO emp : rosterResponse.getEmpList()) {
	        RosterChartResponse.EmployeeRow row = new RosterChartResponse.EmployeeRow();
	        row.setEmpName(emp.getEmpName());
	        
	        Map<String, String> statusMap = new LinkedHashMap<>();
	        int shiftsAssignedInCurrentWeek = 0;
	        int currentWeekOfYear = -1;
	
	        Set<LocalDate> leaves = emp.getLeavesList().stream()
	            .map(LeaveDTO::getLeaveDate)
	            .collect(Collectors.toSet());
	
	        // Rotational Offset: prevents everyone from working Mon-Fri every week
	        int rotationOffset = empIndex % 3; 
	
	        for (LocalDate date : allDates) {
	            // Check if we have moved to a new week
	            int weekOfYear = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
	            if (weekOfYear != currentWeekOfYear) {
	                currentWeekOfYear = weekOfYear;
	                shiftsAssignedInCurrentWeek = 0; // Reset weekly limit
	            }
	
	            boolean isWeekend = (date.getDayOfWeek() == DayOfWeek.SATURDAY || 
	                                 date.getDayOfWeek() == DayOfWeek.SUNDAY);
	
	            if (leaves.contains(date)) {
	                statusMap.put(date.toString(), "LEAVE");
	            } else if (!rosterResponse.getIncludeWeekends() && isWeekend) {
	                statusMap.put(date.toString(), "WEEKEND_OFF");
	            } else if (shiftsAssignedInCurrentWeek < rosterResponse.getDaysToAssignEachEmp()) {
	                
	                // Rotational Logic: Skip days based on offset to rotate start times
	                if (date.getDayOfMonth() % (rotationOffset + 2) == 0 && shiftsAssignedInCurrentWeek == 0) {
	                     statusMap.put(date.toString(), "OFF");
	                } else {
	                    statusMap.put(date.toString(), "SHIFT");
	                    shiftsAssignedInCurrentWeek++;
	                }
	            } else {
	                statusMap.put(date.toString(), "OFF");
	            }
	        }
	        row.setDayStatus(statusMap);
	        rows.add(row);
	        empIndex++;
	    }
	
	    dto.setRows(rows);
	    return dto;
	}

}
