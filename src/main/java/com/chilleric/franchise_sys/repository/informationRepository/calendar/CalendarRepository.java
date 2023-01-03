package com.chilleric.franchise_sys.repository.informationRepository.calendar;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CalendarRepository {
    Optional<List<Calendar>> getCalendars(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    Optional<List<Calendar>> getCalendarsByAssigneeId(String userId);

    void insertAndUpdate(Calendar calendar);

    long getTotalPage(Map<String, String> allParams);
}
