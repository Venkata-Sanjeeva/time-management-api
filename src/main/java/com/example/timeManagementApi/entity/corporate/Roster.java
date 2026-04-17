package com.example.timeManagementApi.entity.corporate;

import java.util.ArrayList;
import java.util.List;

import com.example.timeManagementApi.enums.Shifts;
import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@OneToMany(mappedBy = "roster", cascade = CascadeType.ALL)
	private List<Employee> allocatedEmployees = new ArrayList<>();
	
	@ElementCollection(targetClass = Shifts.class)
	@CollectionTable(name = "roster_shifts", joinColumns = @JoinColumn(name = "roster_id"))
	@Enumerated(EnumType.STRING) // Saves as "MORNING" instead of 0
	private List<Shifts> shifts;
	
	public void addEmployee(Employee employee) {
	    if (allocatedEmployees == null) {
	        allocatedEmployees = new ArrayList<>();
	    }
	    allocatedEmployees.add(employee);
	    employee.setRoster(this); // Crucial: This sets the foreign key
	}
}
