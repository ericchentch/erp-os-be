package com.chilleric.franchise_sys.repository.systemRepository.accessability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class AccessabilityRepositoryImpl extends AbstractRepo implements AccessabilityRepository {
  @Override
  public Optional<Accessability> getAccessability(String userId, String targetId) {
    try {
      ObjectId user_id = new ObjectId(userId);
      ObjectId target_id = new ObjectId(targetId);
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(user_id).and("targetId").in(target_id));
      return systemFindOne(query, Accessability.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id");
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<Accessability>> getListTargetId(String userId) {
    try {
      ObjectId user_id = new ObjectId(userId);
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(user_id));
      return systemFind(query, Accessability.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id");
      return Optional.empty();
    }
  }

  @Override
  public void addNewAccessability(Accessability accessability) {
    systemDBTemplate.save(accessability, "accessability");
  }

  @Override
  public void deleteAccessability(String id) {
    Map<String, String> params = new HashMap<>();
    params.put("_id", id);
    Query query = generateQueryMongoDB(params, Accessability.class, "", "", 0, 0);
    systemDBTemplate.remove(query, Accessability.class);
  }

  @Override
  public Optional<List<Accessability>> getListAccessability(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Accessability.class, keySort, sortField, page, pageSize);
    return systemFind(query, Accessability.class);
  }
}
