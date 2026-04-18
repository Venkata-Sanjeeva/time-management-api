package com.example.timeManagementApi.controller.corporate;

import java.security.Principal;
import java.util.List;

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
import com.example.timeManagementApi.request.corporate.RosterRequestVersion1;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.RosterChartResponse;
import com.example.timeManagementApi.response.corporate.RosterDTO;
import com.example.timeManagementApi.response.corporate.RosterResponse;
import com.example.timeManagementApi.service.impl.corporate.RosterServiceImpl;

@RestController
@RequestMapping("/roster")
public class RosterController {
	
	@Autowired
	private RosterServiceImpl rosterService;
	
	@PostMapping("/create")
	public ResponseEntity<GlobalResponse<RosterResponse>> createRoster(
			@RequestBody RosterRequest rosterReq,
			Principal principal) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<RosterResponse>builder()
						.status(HttpStatus.CREATED.value())
						.data(rosterService.saveRosterInDB(principal.getName(), rosterReq))
						.message("Roster Created Successfully!")
						.build());
	}
	
	@PostMapping("/create/v1")
	public ResponseEntity<GlobalResponse<RosterResponse>> createRosterV1(
			@RequestBody RosterRequestVersion1 rosterReq,
			Principal principal) {
		System.out.println(rosterReq);
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(GlobalResponse.<RosterResponse>builder()
//						.status(HttpStatus.CREATED.value())
//						.data(rosterService.saveRosterInDB(principal.getName(), rosterReq))
//						.message("Roster Created Successfully!")
//						.build());
		return null;
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
	
	@GetMapping("/read/all")
	public ResponseEntity<GlobalResponse<List<RosterDTO>>> getAllRostersForRespectiveUser(Principal principal) {
	    return ResponseEntity.status(HttpStatus.OK)
	    		.body(new GlobalResponse<>(200, "All Rosters Fetched Successfully!", rosterService.readAllRostersRelatesToUserEmail(principal.getName())));
	}
	
	@GetMapping("/generate/{rosterId}/chart")
	public ResponseEntity<GlobalResponse<RosterChartResponse>> getRosterChart(@PathVariable String rosterId) {
	    RosterChartResponse chartData = rosterService.generateRosterChart(rosterId);
	    return ResponseEntity.status(HttpStatus.OK)
	    		.body(new GlobalResponse<>(200, "Chart Generated Successfully!", chartData));
	}
}
