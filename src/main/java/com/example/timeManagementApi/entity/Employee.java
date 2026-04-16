package com.example.timeManagementApi.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "employee")
@Entity
@Getter
@Setter
public class Employee {
	
	@Id
	private String id = IdentifierGenerator.generate("Emp");
	
	@Column(nullable = false, length = 100, unique = true)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String designation;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Leave> leavesTaken = new ArrayList<Leave>();
	
//	@ManyToOne
//	@JoinColumn(name = "roster_id")
//	private Roster roster;
}
