package com.chilleric.franchise_sys.repository.information_repository.bill;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BillRepository {
  Optional<List<Bill>> getBills(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);

  void insertAndUpdate(Bill bill);

  long getTotalPage(Map<String, String> allParams);

}
