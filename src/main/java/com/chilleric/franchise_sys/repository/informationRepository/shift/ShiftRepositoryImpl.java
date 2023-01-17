package com.chilleric.franchise_sys.repository.informationRepository.shift;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ShiftRepositoryImpl extends AbstractRepo implements ShiftRepository {
  @Override
  public Optional<List<Shift>> getShifts(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Shift.class, keySort, sortField, page, pageSize);
    return informationFind(query, Shift.class);
  }

  @Override
  public void insertAndUpdate(Shift shift) {
    informationDBTemplate.save(shift, "shifts");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Shift.class, "", "", 0, 0);
    return informationDBTemplate.count(query, Shift.class);
  }

  @Override
  public void deleteShift(String shiftId) {
    try {
      ObjectId _id = new ObjectId(shiftId);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(_id));
      informationDBTemplate.remove(query, Shift.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type_id");
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }
  }

}
