package com.example.timeManagementApi.service.interfaces.corporate.version0;

import java.util.List;

import com.example.timeManagementApi.request.corporate.version0.RosterRequest;
import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.version0.RosterDTO;
import com.example.timeManagementApi.response.corporate.version0.RosterResponse;

public interface RosterService {
	RosterResponse saveRosterInDB(String userEmail, RosterRequest rosterReq);
	
	RosterResponse updateRoster(String rosterId, RosterRequest rosterReq);
	
	RosterResponse readRoster(String rosterId);
	
	RosterChartResponse generateRosterChart(String rosterId);
	
	List<RosterDTO> readAllRostersRelatesToUserEmail(String userEmail);
	
	void deleteRoster(String rosterId);
}
