package com.example.timeManagementApi.repository.corporate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.Roster;

public interface RosterRepository extends JpaRepository<Roster, String> {

}
