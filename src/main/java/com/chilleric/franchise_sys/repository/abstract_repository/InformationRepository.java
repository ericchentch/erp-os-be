package com.chilleric.franchise_sys.repository.abstract_repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;

public class InformationRepository<T> extends AbstractRepository {

  public Class<T> g() throws RuntimeException {
    ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

    return (Class<T>) superclass.getActualTypeArguments()[0];
  }

  public Optional<List<T>> getListOrEntity(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, g(), keySort, sortField, page, pageSize);
    return informationFind(query, g());
  }

  public Optional<List<T>> getListByAttribute(String value, String attribute) {
    Query query =
        generateQueryMongoDB(Map.ofEntries(Map.entry(attribute, value)), g(), "", "", 0, 0);
    List<T> list = informationFind(query, g()).get();
    if (list.size() > 0) {
      return Optional.of(list);
    }
    return Optional.empty();
  }

  public Optional<T> getEntityByAttribute(String value, String attribute) {
    Query query =
        generateQueryMongoDB(Map.ofEntries(Map.entry(attribute, value)), g(), "", "", 0, 0);
    List<T> list = informationFind(query, g()).get();
    if (list.size() > 0) {
      return Optional.of(list.get(0));
    }
    return Optional.empty();
  }

  public void insertAndUpdate(T entity) {
    informationDBTemplate.save(entity);
  }

  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, g(), "", "", 0, 0);
    long total = informationDBTemplate.count(query, g());
    return total;
  }

  public void deleteById(String id) {
    try {
      ObjectId _id = new ObjectId(id);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(_id));
      informationDBTemplate.remove(query, g());
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type_id");
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }
  }
}
