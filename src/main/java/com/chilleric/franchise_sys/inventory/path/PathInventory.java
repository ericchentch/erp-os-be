package com.chilleric.franchise_sys.inventory.path;

import java.util.Optional;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;

public interface PathInventory {
  Optional<Path> findPathByLabel(String label);

  Optional<Path> findPathByPath(String path);

  Optional<Path> findPathById(String id);
}
