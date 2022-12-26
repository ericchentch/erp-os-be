package com.chilleric.franchise_sys.repository.informationRepository.task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class TaskRepositoryImpl extends AbstractRepo implements TaskRepository {

    @Override
    public Optional<List<Task>> getTasks(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Task.class, keySort, sortField, page, pageSize);
        return informationFind(query, Task.class);
    }

    @Override
    public void insertAndUpdate(Task task) {
        informationDBTemplate.save(task, "tasks");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Task.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Task.class);
    }

}
