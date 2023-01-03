package com.chilleric.franchise_sys.dto.calendar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponse {
    private String id;
    private int year;
    private int month;

    private String assigneeId;
    private List<AssignmentResponse> assignments;
}
