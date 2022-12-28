package com.chilleric.franchise_sys.repository.permission;

import static java.util.Map.entry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.permission.PermissionRequest;
import com.chilleric.franchise_sys.dto.permission.PermissionResponse;
import com.chilleric.franchise_sys.dto.user.UserRequest;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.repository.AbstractRepo;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

@Repository
public class PermissionRepositoryImpl extends AbstractRepo implements PermissionRepository {

  @Override
  public Optional<List<Permission>> getPermissions(Map<String, String> allParams, String keySort,
      int page, int pageSize, String sortField) {
    Query query =
        generateQueryMongoDB(allParams, Permission.class, keySort, sortField, page, pageSize);
    return systemFind(query, Permission.class);
  }

  @Override
  public Optional<Permission> getPermissionById(String id) {
    try {
      ObjectId _id = new ObjectId(id);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(_id));
      return systemFindOne(query, Permission.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type_id");
      return Optional.empty();
    }
  }

  @Override
  public void insertAndUpdate(Permission permission) {
    systemDBTemplate.save(permission, "permissions");
  }

  @Override
  public void deletePermission(String id) {
    try {
      ObjectId _id = new ObjectId(id);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(_id));
      systemDBTemplate.remove(query, Permission.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type_id");
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }
  }

  @Override
  public Map<String, List<ViewPoint>> getViewPointSelect() {
    List<Class<?>> viewPointList = List.of(UserResponse.class, PermissionResponse.class);
    Map<String, List<ViewPoint>> result = new HashMap<>();
    viewPointList.forEach(clazz -> {
      List<ViewPoint> attributes = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
        attributes.add(new ViewPoint(field.getName(), field.getName()));
      }
      result.put(clazz.getSimpleName(), attributes);
    });
    return removeId(result);
  }

  @Override
  public Map<String, List<ViewPoint>> getEditableSelect() {
    List<Class<?>> viewPointList = List.of(UserRequest.class, PermissionRequest.class);
    Map<String, List<ViewPoint>> result = new HashMap<>();
    viewPointList.forEach(clazz -> {
      List<ViewPoint> attributes = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
        attributes.add(new ViewPoint(field.getName(), field.getName()));
      }
      result.put(clazz.getSimpleName(), attributes);
    });
    return removeId(result);
  }

  @Override
  public Optional<List<Permission>> getPermissionByUserId(String userId) {
    try {
      ObjectId user_id = new ObjectId(userId);
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").in(user_id));
      return systemFind(query, Permission.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("wrong type user id or feature id");
      return Optional.empty();
    }
  }

  @Override
  public long getTotal(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Permission.class, "", "", 0, 0);
    return systemDBTemplate.count(query, Permission.class);
  }

  private Map<String, List<ViewPoint>> removeId(Map<String, List<ViewPoint>> thisView) {
    return thisView.entrySet().stream().map((key) -> {
      List<ViewPoint> newValue = key.getValue().stream()
          .filter(viewList -> viewList.getKey().compareTo("id") != 0).collect(Collectors.toList());
      return entry(key.getKey(), newValue);
    }).collect(
        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
  }

}
