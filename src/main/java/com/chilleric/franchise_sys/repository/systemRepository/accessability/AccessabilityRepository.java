package com.chilleric.franchise_sys.repository.systemRepository.accessability;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccessabilityRepository {
  Optional<Accessability> getAccessability(String userId, String targetId);

  Optional<List<Accessability>> getListTargetId(String userId);

  Optional<List<Accessability>> getListAccessability(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField);


  void addNewAccessability(Accessability accessability);

  void deleteAccessability(String id);
}
