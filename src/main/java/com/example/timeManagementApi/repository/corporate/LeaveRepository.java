package com.example.timeManagementApi.repository.corporate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, String> {

}
