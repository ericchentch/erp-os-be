package com.chilleric.franchise_sys.repository.crmRepository.bill;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class BillRepositoryImpl extends AbstractRepo implements BillRepository {

  @Override
  public Optional<List<Bill>> getBills(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Bill.class, keySort, sortField, page, pageSize);
    return crmFind(query, Bill.class);
  }

  @Override
  public void insertAndUpdate(Bill bill) {
    crmDBTemplate.save(bill);
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Bill.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Bill.class);
  }

}
