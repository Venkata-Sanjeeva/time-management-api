package com.example.timeManagementApi.entity.corporate.version0;

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
import jakarta.validation.constraints.Email;
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
	
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	
	@Column(nullable = false, length = 50)
	private String designation;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Leave> leavesTaken = new ArrayList<Leave>();
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Roster roster;
	
}
