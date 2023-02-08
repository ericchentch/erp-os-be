package com.chilleric.franchise_sys.repository.crm_repository.inventoryConfirmation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InventoryConfirmationRepository {
  Optional<List<InventoryConfirmation>> getInventoryConfirmations(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField);

  void insertAndUpdate(InventoryConfirmation inventoryConfirmation);

  long getTotalPage(Map<String, String> allParams);
}
