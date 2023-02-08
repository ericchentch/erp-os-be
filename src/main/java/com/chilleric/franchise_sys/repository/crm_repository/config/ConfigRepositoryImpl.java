package com.chilleric.franchise_sys.repository.crm_repository.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ConfigRepositoryImpl extends AbstractRepo implements ConfigRepository {

  @Override
  public Optional<List<Config>> getConfigs(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Config.class, keySort, sortField, page, pageSize);

    return crmFind(query, Config.class);
  }

  @Override
  public void insertAndUpdate(Config config) {
    crmDBTemplate.save(config, "configs");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Config.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Config.class);
  }

  @Override
  public Optional<Config> getConfigByHotel(String hotelId) {
    try {
      ObjectId hotel_id = new ObjectId(hotelId);
      Query query = new Query();
      query.addCriteria(Criteria.where("hotelId").is(hotel_id));
      return Optional.of(crmFind(query, Config.class).get().get(0));
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type hotel id");
      return Optional.empty();
    }
  }

}
