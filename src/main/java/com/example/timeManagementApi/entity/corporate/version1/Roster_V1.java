package com.example.timeManagementApi.entity.corporate.version1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.timeManagementApi.entity.User;
import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "rosterV1")
@Entity
@Getter
@Setter
public class Roster_V1 {
	
	@Id
	private String id = IdentifierGenerator.generate("Ros");
	
	@Column(nullable = false, length = 15)
	private LocalDate startDate;
	
	@Column(nullable = false, length = 4)
	private LocalDate endDate;
	
	@Column(nullable = false)
	private Integer daysToAssignEachEmp;
	
	@Column(nullable = false)
	private Integer weekdaysOff;
	
	@Column(nullable = false)
	private Boolean includeWeekends;
	
	
	@Column(nullable = false)
	private Boolean seniorStaffPresence;
	
	@OneToMany(mappedBy = "roster", cascade = CascadeType.ALL)
	private List<Employee_V1> allocatedEmployees = new ArrayList<>();
	
	@OneToMany(mappedBy = "roster", cascade = CascadeType.ALL)
	private List<Shift_V1> shifts = new ArrayList<Shift_V1>();
	
	@ManyToOne
	private User user;
	
	public void addEmployee(Employee_V1 employee) {
	    if (allocatedEmployees == null) {
	        allocatedEmployees = new ArrayList<>();
	    }
	    allocatedEmployees.add(employee);
	    employee.setRoster(this); // Crucial: This sets the foreign key
	}
}
