package com.example.timeManagementApi.repository.corporate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.Roster;

public interface RosterRepository extends JpaRepository<Roster, String> {
	List<Roster> findByUserEmail(String userEmail);
}
