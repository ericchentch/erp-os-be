package com.chilleric.franchise_sys.repository.crm_repository.discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountRepository {
  Optional<List<Discount>> getDiscounts(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);


  void insertAndUpdate(Discount discount);

  long getTotalPage(Map<String, String> allParams);
}
