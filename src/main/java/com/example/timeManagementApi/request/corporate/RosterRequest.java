package com.example.timeManagementApi.request.corporate;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RosterRequest {
	
	private Boolean requireSeniorOnShift;
	private Map<String, Boolean> shifts;
	
	private RosterBaseDTO base;
	
	private RosterConstraintsDTO constraints;
}
