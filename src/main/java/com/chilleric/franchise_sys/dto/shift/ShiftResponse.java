package com.chilleric.franchise_sys.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponse {
    private String id;

    private String shiftName;

    private String startDate;

    private String endDate;
}
