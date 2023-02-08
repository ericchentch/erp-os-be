package com.chilleric.franchise_sys.repository.crm_repository.inventoryConfirmation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import com.chilleric.franchise_sys.repository.AbstractRepo;

public class InventoryConfirmationRepositoryImpl extends AbstractRepo
    implements InventoryConfirmationRepository {

  @Override
  public Optional<List<InventoryConfirmation>> getInventoryConfirmations(
      Map<String, String> allParams, String keySort, int page, int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, InventoryConfirmation.class, keySort, sortField,
        page, pageSize);
    return crmFind(query, InventoryConfirmation.class);
  }

  @Override
  public void insertAndUpdate(InventoryConfirmation inventoryConfirmation) {
    crmDBTemplate.save(inventoryConfirmation, "inventory_confirmations");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, InventoryConfirmation.class, "", "", 0, 0);
    return crmDBTemplate.count(query, InventoryConfirmation.class);
  }

}
