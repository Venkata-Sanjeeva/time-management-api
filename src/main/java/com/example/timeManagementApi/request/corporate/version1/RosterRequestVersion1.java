package com.example.timeManagementApi.request.corporate.version1;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Main Payload Class
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RosterRequestVersion1 {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> employeeIds;
    private List<String> seniors;
    private List<ShiftDTO> shifts;
    private ConstraintsDTO constraints;

    // --- Add STATIC here ---
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShiftDTO {
        private String id;
        private String name;
        private String start; 
        private String end;
        private boolean active;
    }
    
    // --- Add STATIC here ---
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConstraintsDTO {
        private Integer daysPerEmployee;
        private Integer offDaysPerRotation;
        private boolean includeWeekends;
        private boolean requireSeniorOnShift;
    }
}
