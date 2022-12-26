package com.chilleric.franchise_sys.repository.informationRepository.inventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InventoryRepository {
    Optional<List<Inventory>> getInventories(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField);

    void insertAndUpdate(Inventory inventory);

    long getTotalPage(Map<String, String> allParams);
}
