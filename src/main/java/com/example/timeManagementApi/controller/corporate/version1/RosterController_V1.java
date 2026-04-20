package com.example.timeManagementApi.controller.corporate.version1;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.timeManagementApi.request.corporate.version1.RosterRequestVersion1;
import com.example.timeManagementApi.response.GlobalResponse;
import com.example.timeManagementApi.response.corporate.version1.RosterDTOV1;
import com.example.timeManagementApi.response.corporate.version1.RosterResponseV1;
import com.example.timeManagementApi.service.impl.corporate.version1.RosterServiceImpl_V1;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roster/v1")
@Tag(name = "Roster Controller V1")
public class RosterController_V1 {
	
	@Autowired
	private RosterServiceImpl_V1 rosterService;
	
	@PostMapping("/create")
	public ResponseEntity<GlobalResponse<RosterResponseV1>> createRoster(
			@RequestBody RosterRequestVersion1 rosterReq,
			Principal principal) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(GlobalResponse.<RosterResponseV1>builder()
						.status(HttpStatus.CREATED.value())
						.data(rosterService.saveRosterInDB_V1(principal.getName(), rosterReq))
						.message("Roster Created Successfully!")
						.build());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GlobalResponse<RosterResponseV1>> readRoster(@PathVariable(name = "id") String rosterId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.<RosterResponseV1>builder()
						.status(HttpStatus.OK.value())
						.data(rosterService.readRoster(rosterId))
						.message("Roster Fetched Successfully!")
						.build()); 
	}
	

	@GetMapping("/read/all")
	public ResponseEntity<GlobalResponse<List<RosterDTOV1>>> getAllRostersForRespectiveUser(Principal principal) {
	    return ResponseEntity.status(HttpStatus.OK)
	    		.body(new GlobalResponse<>(200, "All Rosters Fetched Successfully!", rosterService.readAllRostersRelatesToUserEmail(principal.getName())));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<GlobalResponse<?>> deleteRosterWithId(@PathVariable(name = "id") String rosterId) {
		rosterService.deleteRoster(rosterId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(GlobalResponse.builder()
						.status(200)
						.data(null)
						.message("Roster with ID: " + rosterId + " deleted successfuly...")
						.build());
	}
	
}
