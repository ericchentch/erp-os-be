package com.chilleric.franchise_sys.repository.system_repository.code;

import com.chilleric.franchise_sys.repository.abstract_repository.SystemRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CodeRepository extends SystemRepository<Code> {

  public Optional<Code> getCodesByType(String userId, String type) {
    List<Code> list = getListOrEntity(
        Map.ofEntries(Map.entry("userId", userId), Map.entry("type", type)),
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

  public Optional<Code> getCodesByCode(String userId, String code) {
    List<Code> list = getListOrEntity(
        Map.ofEntries(Map.entry("userId", userId), Map.entry("code", code)),
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
