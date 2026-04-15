package com.example.timeManagementApi.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String designation;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Leave> leavesTaken = new ArrayList<Leave>();
	
//	@ManyToOne
//	@JoinColumn(name = "roster_id")
//	private Roster roster;
}
