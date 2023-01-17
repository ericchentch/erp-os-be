package com.chilleric.franchise_sys.repository.informationRepository.task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskRepository {
  Optional<List<Task>> getTasks(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);

  void insertAndUpdate(Task task);

  long getTotalPage(Map<String, String> allParams);

  void delete(String taskId);
}
