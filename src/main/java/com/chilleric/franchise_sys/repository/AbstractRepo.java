package com.chilleric.franchise_sys.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.log.AppLogger;
import com.chilleric.franchise_sys.log.LoggerFactory;
import com.chilleric.franchise_sys.log.LoggerType;

public abstract class AbstractRepo {

  @Autowired
  @Qualifier("mongo_system_template")
  protected MongoTemplate systemDBTemplate;

  @Autowired
  @Qualifier("mongo_information_template")
  protected MongoTemplate informationDBTemplate;

  @Autowired
  @Qualifier("mongo_crm_template")
  protected MongoTemplate crmDBTemplate;

  protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

  protected Query generateQueryMongoDB(Map<String, String> allParams, Class<?> clazz,
      String keySort, String sortField, int page, int pageSize) {
    Query query = new Query();
    Field[] fields = clazz.getDeclaredFields();
    List<Criteria> allCriteria = new ArrayList<>();
    int isSort = 0;
    for (Map.Entry<String, String> items : allParams.entrySet()) {
      for (Field field : fields) {
        if (field.getName().compareTo(sortField) == 0) {
          isSort = 1;
        }
        if (field.getName().compareTo(items.getKey()) == 0) {
          List<Criteria> conditionCriterias = generateConditionCriterias(field, items);
          allCriteria.add(new Criteria().orOperator(conditionCriterias));
        }
      }
    }
    if (allCriteria.size() > 0) {
      query.addCriteria(new Criteria().andOperator(allCriteria));
    }
    if (isSort == 1 && keySort.trim().compareTo("") != 0 && keySort.trim().compareTo("ASC") == 0) {
      query.with(Sort.by(Sort.Direction.ASC, sortField));
    }
    if (isSort == 1 && keySort.trim().compareTo("") != 0 && keySort.trim().compareTo("DESC") == 0) {
      query.with(Sort.by(Sort.Direction.DESC, sortField));
    }
    if (page > 0 && pageSize > 0) {
      query.skip((long) (page - 1) * pageSize).limit(pageSize);
    }
    return query;
  }

  protected <T> Optional<List<T>> systemFind(Query query, Class<T> clazz) {
    try {
      List<T> result = systemDBTemplate.find(query, clazz);
      return Optional.of(result);
    } catch (IllegalArgumentException | NullPointerException e) {
      APP_LOGGER.error(e.getMessage());
      return Optional.empty();
    }
  }

  protected <T> Optional<T> systemFindOne(Query query, Class<T> clazz) {
    try {
      T result = systemDBTemplate.findOne(query, clazz);
      return Optional.of(result);
    } catch (IllegalArgumentException | NullPointerException e) {
      APP_LOGGER.error(e.getMessage());
      return Optional.empty();
    }
  }

  protected <T> Optional<List<T>> crmFind(Query query, Class<T> clazz) {
    try {
      List<T> result = crmDBTemplate.find(query, clazz);
      return Optional.of(result);
    } catch (IllegalArgumentException | NullPointerException e) {
      APP_LOGGER.error(e.getMessage());
      return Optional.empty();
    }
  }

  protected <T> Optional<List<T>> informationFind(Query query, Class<T> clazz) {
    try {
      List<T> result = informationDBTemplate.find(query, clazz);
      return Optional.of(result);
    } catch (IllegalArgumentException | NullPointerException e) {
      APP_LOGGER.error(e.getMessage());
      return Optional.empty();
    }
  }

  List<Criteria> generateConditionCriterias(Field field, Map.Entry<String, String> items) {
    String[] values = items.getValue().split(",");
    List<Criteria> conditionCriteria = new ArrayList<>();
    if (field.getType() == ObjectId.class) {
      for (String value : values) {
        try {
          conditionCriteria.add(Criteria.where(items.getKey()).is(new ObjectId(value)));
        } catch (IllegalArgumentException e) {
          APP_LOGGER.error(e.getMessage());
          throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
      }
    }
    if (field.getType() == Boolean.class) {
      for (String s : values) {
        try {
          boolean value = Boolean.parseBoolean(s);
          conditionCriteria.add(Criteria.where(items.getKey()).is(value));
        } catch (Exception e) {
          APP_LOGGER.error("error parsing value boolean");
          throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
      }
    }
    if (field.getType() == int.class) {
      for (String s : values) {
        try {
          int value = Integer.parseInt(s);
          conditionCriteria.add(Criteria.where(items.getKey()).is(value));
        } catch (Exception e) {
          APP_LOGGER.error("error parsing value int");
          throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
      }
    }
    if (field.getType() == String.class) {
      for (String value : values) {
        conditionCriteria.add(Criteria.where(items.getKey()).is(value));
      }
    }
    if (field.getType() == ArrayList.class) {
      for (String value : values) {
        try {
          conditionCriteria.add(Criteria.where(items.getKey()).in(value));
        } catch (NullPointerException e) {
          APP_LOGGER.error("error array values");
          throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        } catch (Exception e) {
          APP_LOGGER.error("error ");
          throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }

      }
    }
    return conditionCriteria;
  }

}
