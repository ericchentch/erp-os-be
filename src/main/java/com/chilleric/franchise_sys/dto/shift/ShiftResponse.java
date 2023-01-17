package com.chilleric.franchise_sys.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponse {
  private String id;

  private String shiftName;

  private String startTime;

  private String endTime;
}
