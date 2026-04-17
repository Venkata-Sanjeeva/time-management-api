package com.example.timeManagementApi.repository.corporate;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.timeManagementApi.entity.corporate.Leave;

import jakarta.transaction.Transactional;

public interface LeaveRepository extends JpaRepository<Leave, String> {
	List<Leave> findByEmployeeId(String empId);
	List<Leave> findByEmployeeIdOrderByLeaveDateDesc(String empId);
	boolean existsByEmployeeIdAndLeaveDate(String empId, LocalDate leaveDate);
	List<Leave> findByEmployee_IdAndLeaveDateBetween(
	        String empId, 
	        LocalDate start, 
	        LocalDate end
	    );
	
	@Modifying // Without this, you will get an InvalidDataAccessApiUsageException
    @Transactional // Required for delete operations. If the deletion fails halfway through, this ensures the database rolls back to its previous state, preventing "partial data loss."
    @Query("DELETE FROM Leave l WHERE l.employee.empId = :empId " +
           "AND l.leaveDate >= :startDate AND l.leaveDate <= :endDate")
    void deleteByEmployeeAndMonth(
        @Param("empId") String empId, 
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
}
