package com.example.timeManagementApi.entity.corporate;

import java.util.List;

import com.example.timeManagementApi.enums.Shifts;
import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "roster")
@Entity
@Getter
@Setter
public class Roster {
	
	@Id
	private String id = IdentifierGenerator.generate("Ros");
	
	@Column(nullable = false, length = 15)
	private String rosterMonth;
	
	@Column(nullable = false, length = 4)
	private String rosterYear;
	
	@Column(nullable = false)
	private Integer daysToAssignEachEmp;
	
	@Column(nullable = false)
	private Integer weekdaysOff;
	
	@Column(nullable = false)
	private Boolean includeWeekends;
	
	
	@Column(nullable = false)
	private Boolean seniorStaffPresence;
	
	@OneToMany(mappedBy = "roster")
	private List<Employee> allocatedEmployees;
	
	private List<Shifts> shifts;
	
//	public void addEmployee(Employee employee) {
//	    allocatedEmployees.add(employee);
//	    employee.setRoster(this);
//	}
}
