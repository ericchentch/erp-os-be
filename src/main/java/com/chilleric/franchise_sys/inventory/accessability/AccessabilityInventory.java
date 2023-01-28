package com.chilleric.franchise_sys.inventory.accessability;

import java.util.List;
import java.util.Optional;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;

public interface AccessabilityInventory {
  Optional<List<Accessability>> getAccessByTargetId(String targetId);
}
