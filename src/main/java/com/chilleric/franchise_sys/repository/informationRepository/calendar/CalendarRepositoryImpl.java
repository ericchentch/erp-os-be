package com.chilleric.franchise_sys.repository.informationRepository.calendar;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class CalendarRepositoryImpl extends AbstractRepo implements CalendarRepository {

    @Override
    public Optional<List<Calendar>> getCalendars(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Calendar.class, keySort, sortField, page, pageSize);
        return informationFind(query, Calendar.class);
    }

    @Override
    public void insertAndUpdate(Calendar calendar) {
        informationDBTemplate.save(calendar, "calendars");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Calendar.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Calendar.class);
    }

    @Override
    public Optional<List<Calendar>> getCalendarsByAssigneeId(String userId) {
        try {
            ObjectId user_id = new ObjectId(userId);
            Query query = new Query();
            query.addCriteria(Criteria.where("assigneeId").is(user_id));
            return informationFind(query, Calendar.class);
        } catch (IllegalArgumentException e) {
            APP_LOGGER.error("Assignee id is wrong type");
            return Optional.empty();
        }
    }

}
