package com.example.timeManagementApi.entity;

import java.time.LocalDate;
import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "leaves")
@Entity
@Getter
@Setter
public class Leave {
	
	@Id
	private String id = IdentifierGenerator.generate("Lea");
	
	@Column(nullable = false)
	private LocalDate leaveDate;
	
	@ManyToOne
	private Employee employee;
}
