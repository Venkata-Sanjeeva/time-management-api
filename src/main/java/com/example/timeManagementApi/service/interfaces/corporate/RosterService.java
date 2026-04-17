package com.example.timeManagementApi.service.interfaces.corporate;

import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.response.corporate.RosterResponse;

public interface RosterService {
	RosterResponse saveRosterInDB(RosterRequest rosterReq);
	
	RosterResponse updateRoster(String rosterId, RosterRequest rosterReq);
	
	RosterResponse readRoster(String rosterId);
	
	void deleteRoster(String rosterId);
}
