package com.example.timeManagementApi.entity.corporate.version1;

import java.time.LocalTime;

import com.example.timeManagementApi.util.IdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "shiftsV1")
@Getter
@Setter
@Entity
public class Shift_V1 {
	
	@Id
	private String id = IdentifierGenerator.generate("sh");
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private LocalTime start;
	
	@Column(nullable = false)
	private LocalTime end;
	
	@Column(nullable = false)
	private Boolean active;
	
	@ManyToOne
	private Roster_V1 roster;
}
