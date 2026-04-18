package com.example.timeManagementApi.request.corporate;

import java.util.List;

import lombok.Data;

import java.time.LocalDate;

// Main Payload Class
@Data
public class RosterRequestVersion1 {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> employeeIds;
    private List<String> seniors;
    private List<ShiftDTO> shifts;
    private ConstraintsDTO constraints;

    // --- Add STATIC here ---
    @Data
    public static class ShiftDTO {
        private String id;
        private String name;
        private String start; 
        private String end;
        private boolean active;
    }
    
    // --- Add STATIC here ---
    @Data
    public static class ConstraintsDTO {
        private Integer daysPerEmployee;
        private Integer offDaysPerRotation;
        private boolean includeWeekends;
        private boolean requireSeniorOnShift;
    }
}
