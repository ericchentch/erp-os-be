package com.chilleric.franchise_sys.service.task;

import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.task.TaskRequest;
import com.chilleric.franchise_sys.dto.task.TaskResponse;

public interface TaskService {
  void createTask(TaskRequest taskRequest);

  void update(TaskRequest taskRequest, String taskId);

  Optional<ListWrapperResponse<TaskResponse>> getTasks(Map<String, String> allParams, int pageSize,
      int page, String keySort, String sortField);

  void delete(String taskId);

  boolean isReferencesValid(String reviewerId, String employeeId, String shiftId);
}
