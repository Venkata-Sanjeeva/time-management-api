package com.example.timeManagementApi.service.interfaces.corporate;

import com.example.timeManagementApi.request.corporate.RosterRequest;

public interface RosterService {
	Object saveRosterInDB(RosterRequest rosterReq);
	
	Object updateRoster(String rosterId, RosterRequest rosterReq);
	
	Object readRoster(String rosterId);
	
	void deleteRoster(String rosterId);
}
