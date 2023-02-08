package com.chilleric.franchise_sys.repository.crm_repository.inventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class InventoryRepositoryImpl extends AbstractRepo implements InventoryRepository {

  @Override
  public Optional<List<Inventory>> getInventories(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Inventory.class, keySort, sortField, page, pageSize);
    return crmFind(query, Inventory.class);
  }

  @Override
  public void insertAndUpdate(Inventory inventory) {
    crmDBTemplate.save(inventory, "inventories");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Inventory.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Inventory.class);
  }

}
