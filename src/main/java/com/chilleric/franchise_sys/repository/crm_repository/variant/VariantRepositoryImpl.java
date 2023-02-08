package com.chilleric.franchise_sys.repository.crm_repository.variant;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class VariantRepositoryImpl extends AbstractRepo implements VariantRepository {

  @Override
  public Optional<List<Variant>> getVariants(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Variant.class, keySort, sortField, page, pageSize);
    return crmFind(query, Variant.class);
  }

  @Override
  public void insertAndUpdate(Variant varian) {
    crmDBTemplate.save(varian, "variants");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Variant.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Variant.class);
  }

}
