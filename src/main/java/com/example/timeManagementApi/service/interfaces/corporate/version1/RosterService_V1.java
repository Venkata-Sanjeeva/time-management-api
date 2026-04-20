package com.example.timeManagementApi.service.interfaces.corporate.version1;

import java.util.List;

import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1;
import com.example.timeManagementApi.response.corporate.version0.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.version0.RosterDTO;
import com.example.timeManagementApi.response.corporate.version1.RosterResponseV1;

public interface RosterService_V1 {
	
	RosterResponseV1 saveRosterInDB_V1(String userEmail, RosterRequestVersion1 rosterReq);
	
	RosterResponseV1 updateRoster(String rosterId, RosterRequestVersion1 rosterReq);
	
	RosterResponseV1 readRoster(String rosterId);
	
	RosterChartResponse generateRosterChart(String rosterId);
	
	List<RosterDTO> readAllRostersRelatesToUserEmail(String userEmail);
	
	void deleteRoster(String rosterId);
}
