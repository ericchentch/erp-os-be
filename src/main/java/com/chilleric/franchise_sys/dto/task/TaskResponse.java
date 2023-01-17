package com.chilleric.franchise_sys.dto.task;

import com.chilleric.franchise_sys.dto.shift.ShiftResponse;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.informationRepository.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
  private String id;

  private UserResponse employee;
  private ShiftResponse shift;
  private UserResponse reviewer;

  private String startTime;
  private String title;
  private String description;
  private TaskStatus status;
}
