package com.chilleric.franchise_sys.repository.crm_repository.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ServiceRepositoryImpl extends AbstractRepo implements ServiceRepository {

  @Override
  public Optional<List<Service>> getServices(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Service.class, keySort, sortField, page, pageSize);

    return crmFind(query, Service.class);
  }

  @Override
  public void insertAndUpdate(Service service) {
    crmDBTemplate.save(service, "services");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Service.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Service.class);
  }

}
