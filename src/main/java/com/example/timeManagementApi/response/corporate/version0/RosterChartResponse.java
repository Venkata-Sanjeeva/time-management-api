package com.example.timeManagementApi.response.corporate.version0;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RosterChartResponse {
    private String rosterId;
    private List<String> dates; // ["2026-05-01", "2026-05-02"...]
    private List<EmployeeRow> rows;

    // Inner class for each employee's row
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeRow {
        private String empName;
        private Map<String, String> dayStatus; // {"2026-05-01": "SHIFT", "2026-05-14": "LEAVE"}
    }
}
