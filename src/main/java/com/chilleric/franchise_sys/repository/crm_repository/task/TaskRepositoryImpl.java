package com.chilleric.franchise_sys.repository.crm_repository.task;

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
public class TaskRepositoryImpl extends AbstractRepo implements TaskRepository {

  @Override
  public Optional<List<Task>> getTasks(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Task.class, keySort, sortField, page, pageSize);
    return crmFind(query, Task.class);
  }

  @Override
  public void insertAndUpdate(Task task) {
    crmDBTemplate.save(task, "tasks");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Task.class, "", "", 0, 0);
    return crmDBTemplate.count(query, Task.class);
  }

  @Override
  public void delete(String taskId) {
    try {
      ObjectId task_id = new ObjectId(taskId);
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(task_id));
      crmDBTemplate.remove(query, Task.class);
    } catch (IllegalArgumentException e) {
      APP_LOGGER.error("Task id is wrong type");
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }
  }

}
