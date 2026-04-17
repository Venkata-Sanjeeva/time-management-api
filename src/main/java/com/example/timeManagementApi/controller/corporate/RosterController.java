package com.example.timeManagementApi.controller.corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timeManagementApi.request.corporate.RosterRequest;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.RosterResponse;
import com.example.timeManagementApi.service.impl.corporate.RosterServiceImpl;

@RestController
@RequestMapping("/roster")
public class RosterController {
	
	@Autowired
	private RosterServiceImpl rosterService;
	
	@PostMapping("/create")
	public ResponseEntity<GlobalResponse<RosterResponse>> createRoster(@RequestBody RosterRequest rosterReq) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<RosterResponse>builder()
						.status(HttpStatus.CREATED.value())
						.data(rosterService.saveRosterInDB(rosterReq))
						.message("Roster Created Successfully!")
						.build());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GlobalResponse<RosterResponse>> readRoster(@PathVariable(name = "id") String rosterId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<RosterResponse>builder()
						.status(HttpStatus.OK.value())
						.data(rosterService.readRoster(rosterId))
						.message("Roster Fetched Successfully!")
						.build()); 
	}
}
