package com.chilleric.franchise_sys.repository.system_repository.accessability;

import com.chilleric.franchise_sys.repository.abstract_repository.SystemRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccessabilityRepository extends SystemRepository<Accessability> {

  public Optional<Accessability> getAccessability(String userId, String targetId) {
    List<Accessability> list = getListOrEntity(
        Map.ofEntries(Map.entry("userId", userId), Map.entry("targetId", targetId)),
        "",
        0,
        0,
        ""
      )
      .get();
    if (list.size() > 0) {
      return Optional.of(list.get(0));
    } else {
      return Optional.empty();
    }
  }
}
