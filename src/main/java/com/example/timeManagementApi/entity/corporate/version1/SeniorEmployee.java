package com.example.timeManagementApi.entity.corporate.version1;

import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Table(
	    name = "senior_employees",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"employee_id", "roster_id"})
	    }
	)
	@Entity
	@Getter
	@Setter
	public class SeniorEmployee {

	    @Id
	    private String id = IdentifierGenerator.generate("sen");
	    
	    // one employee can be designated as a "Senior" many times (once for each different roster).
	    @ManyToOne
	    @JoinColumn(name = "employee_id")
	    private Employee_V1 employee;
	    
	    @ManyToOne
	    @JoinColumn(name = "roster_id")
	    private Roster_V1 roster;
	}