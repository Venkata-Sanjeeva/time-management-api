package com.example.timeManagementApi.entity.corporate.version1;

import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "senior_employees")
@Entity
@Getter
@Setter
public class SeniorEmployee {

	@Id
	private String id = IdentifierGenerator.generate("sen");
	
	@OneToOne
	private Employee_V1 employee;
	
	@ManyToOne
	private Roster_V1 roster;
}
