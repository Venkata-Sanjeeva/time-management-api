package com.example.timeManagementApi.service.interfaces.corporate;

import java.util.List;

import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.response.corporate.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.RosterDTO;
import com.example.timeManagementApi.response.corporate.RosterResponse;

public interface RosterService {
	RosterResponse saveRosterInDB(String userEmail, RosterRequest rosterReq);
	
	RosterResponse updateRoster(String rosterId, RosterRequest rosterReq);
	
	RosterResponse readRoster(String rosterId);
	
	RosterChartResponse generateRosterChart(String rosterId);
	
	List<RosterDTO> readAllRostersRelatesToUserEmail(String userEmail);
	
	void deleteRoster(String rosterId);
}
