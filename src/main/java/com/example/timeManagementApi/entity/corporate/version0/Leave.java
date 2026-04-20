package com.example.timeManagementApi.entity.corporate.version0;

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
		name = "leaves",
		uniqueConstraints = {
		        @UniqueConstraint(columnNames = {"leaveDate", "employee_id"})
		    }
)
@Entity
@Getter
@Setter
public class Leave {
	
	@Id
	private String id = IdentifierGenerator.generate("Lea");
	
	@Column(nullable = false)
	private LocalDate leaveDate;
	
	@ManyToOne
	@JoinColumn(name = "employee_id") // Explicitly naming the column for the constraint
	private Employee employee;
}
