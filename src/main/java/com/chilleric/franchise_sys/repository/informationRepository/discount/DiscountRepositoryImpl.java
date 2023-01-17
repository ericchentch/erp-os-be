package com.chilleric.franchise_sys.repository.informationRepository.discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class DiscountRepositoryImpl extends AbstractRepo implements DiscountRepository {

  @Override
  public Optional<List<Discount>> getDiscounts(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Discount.class, keySort, sortField, page, pageSize);
    return informationFind(query, Discount.class);
  }

  @Override
  public void insertAndUpdate(Discount discount) {
    informationDBTemplate.save(discount);
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Discount.class, "", "", 0, 0);
    return informationDBTemplate.count(query, Discount.class);
  }

}
