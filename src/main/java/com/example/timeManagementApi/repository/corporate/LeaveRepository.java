package com.example.timeManagementApi.repository.corporate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.timeManagementApi.entity.corporate.Leave;

public interface LeaveRepository extends JpaRepository<Leave, String> {

}
