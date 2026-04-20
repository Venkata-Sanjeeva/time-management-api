package com.example.timeManagementApi.entity.corporate.version1;

import java.time.LocalDate;

import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Table(
		name = "leavesV1",
		uniqueConstraints = {
		        @UniqueConstraint(columnNames = {"leaveDate", "employeeV1_id"})
		    }
)
@Entity
@Getter
@Setter
public class Leave_V1 {
	
	@Id
	private String id = IdentifierGenerator.generate("Lea");
	
	@Column(nullable = false)
	private LocalDate leaveDate;
	
	@ManyToOne
	@JoinColumn(name = "employeeV1_id") // Explicitly naming the column for the constraint
	private Employee_V1 employee;
}