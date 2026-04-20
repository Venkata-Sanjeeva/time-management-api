package com.example.timeManagementApi.repository.corporate.version1;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.timeManagementApi.entity.corporate.version1.Leave_V1;

import jakarta.transaction.Transactional;

public interface LeaveRepository_V1 extends JpaRepository<Leave_V1, String>{
	List<Leave_V1> findByEmployeeId(String empId);
	List<Leave_V1> findByEmployeeIdOrderByLeaveDateDesc(String empId);
	boolean existsByEmployeeIdAndLeaveDate(String empId, LocalDate leaveDate);
	List<Leave_V1> findByEmployee_IdAndLeaveDateBetween(
	        String empId, 
	        LocalDate start, 
	        LocalDate end
	    );
	
	@Modifying // Without this, you will get an InvalidDataAccessApiUsageException
    @Transactional // Required for delete operations. If the deletion fails halfway through, this ensures the database rolls back to its previous state, preventing "partial data loss."
    @Query("DELETE FROM Leave l WHERE l.employee.Id = :empId " +
           "AND l.leaveDate >= :startDate AND l.leaveDate <= :endDate")
    void deleteByEmployeeAndMonth(
        @Param("empId") String empId, 
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
}
