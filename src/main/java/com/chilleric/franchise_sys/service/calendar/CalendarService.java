package com.chilleric.franchise_sys.service.calendar;

import java.util.Optional;
import com.chilleric.franchise_sys.dto.calendar.CalendarRequest;
import com.chilleric.franchise_sys.dto.calendar.CalendarResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;

public interface CalendarService {
    void createCalendar(CalendarRequest calendarRequest);

    Optional<CalendarResponse> getCalendarById(String calendarId);

    Optional<ListWrapperResponse<CalendarResponse>> getCalendarByUserId(String userId,
            String keySort, int page, int pageSize, String sortField);


}
