package com.chilleric.franchise_sys.repository.system_repository.code;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.abstract_repository.SystemRepository;

@Repository
public class CodeRepository extends SystemRepository<Code> {

  public Optional<Code> getCodesByType(String userId, String type) {
    try {
      ObjectId user_id = new ObjectId(userId);
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(user_id).and("type").is(type));
      return systemFindOne(query, Code.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id");
      return Optional.empty();
    }
  }

  public Optional<Code> getCodesByCode(String userId, String code) {
    try {
      ObjectId user_id = new ObjectId(userId);
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(user_id).and("code").is(code));
      return systemFindOne(query, Code.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id");
      return Optional.empty();
    }
  }

}
