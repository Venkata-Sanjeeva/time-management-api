package com.example.timeManagementApi.repository.corporate.version1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.version1.Roster_V1;

public interface RosterRepository_V1 extends JpaRepository<Roster_V1, String>{
	List<Roster_V1> findByUserEmail(String userEmail);
}
