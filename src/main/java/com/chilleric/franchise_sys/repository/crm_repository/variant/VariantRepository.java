package com.chilleric.franchise_sys.repository.crm_repository.variant;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VariantRepository {
  Optional<List<Variant>> getVariants(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);

  void insertAndUpdate(Variant varian);

  long getTotalPage(Map<String, String> allParams);
}
