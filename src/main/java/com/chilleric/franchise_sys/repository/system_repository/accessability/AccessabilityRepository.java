package com.chilleric.franchise_sys.repository.system_repository.accessability;

import com.chilleric.franchise_sys.repository.abstract_repository.SystemRepository;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AccessabilityRepository extends SystemRepository<Accessability> {

  public Optional<Accessability> getAccessability(String userId, String targetId) {
    try {
      ObjectId user_id = new ObjectId(userId);
      ObjectId target_id = new ObjectId(targetId);
      Query query = new Query();
      query.addCriteria(
        Criteria.where("userId").is(user_id).and("targetId").in(target_id)
      );
      return systemFindOne(query, Accessability.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id");
      return Optional.empty();
    }
  }
}
