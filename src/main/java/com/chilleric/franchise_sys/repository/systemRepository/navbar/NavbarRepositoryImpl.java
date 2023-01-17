package com.chilleric.franchise_sys.repository.systemRepository.navbar;

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
public class NavbarRepositoryImpl extends AbstractRepo implements NavbarRepository {

  @Override
  public Optional<List<Navbar>> getNavbarList(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Navbar.class, keySort, sortField, page, pageSize);
    return systemFind(query, Navbar.class);
  }

  @Override
  public void insertAndUpdate(Navbar navbar) {
    systemDBTemplate.save(navbar);

  }

  @Override
  public void deletePath(String id) {
    try {
      ObjectId _id = new ObjectId(id);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(_id));
      systemDBTemplate.remove(query, Navbar.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type_id");
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }

  }

  @Override
  public long getTotal(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Navbar.class, "", "", 0, 0);
    return systemDBTemplate.count(query, Navbar.class);
  }

}
