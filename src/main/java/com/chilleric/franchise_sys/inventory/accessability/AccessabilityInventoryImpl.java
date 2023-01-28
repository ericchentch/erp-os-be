package com.chilleric.franchise_sys.inventory.accessability;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.AccessabilityRepository;

@Service
public class AccessabilityInventoryImpl extends AbstractInventory<AccessabilityRepository>
    implements AccessabilityInventory {

  @Override
  public Optional<List<Accessability>> getAccessByTargetId(String targetId) {
    List<Accessability> accesses = repository
        .getListAccessability(Map.ofEntries(entry("targetId", targetId)), "", 0, 0, "").get();
    if (accesses.size() == 0)
      return Optional.empty();
    return Optional.of(accesses);
  }

}
