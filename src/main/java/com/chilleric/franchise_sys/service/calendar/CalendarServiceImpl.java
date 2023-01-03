package com.chilleric.franchise_sys.service.calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.calendar.AssignmentResponse;
import com.chilleric.franchise_sys.dto.calendar.CalendarRequest;
import com.chilleric.franchise_sys.dto.calendar.CalendarResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.informationRepository.calendar.Assignment;
import com.chilleric.franchise_sys.repository.informationRepository.calendar.Calendar;
import com.chilleric.franchise_sys.repository.informationRepository.calendar.CalendarRepository;
import com.chilleric.franchise_sys.service.AbstractService;

public class CalendarServiceImpl extends AbstractService<CalendarRepository>
		implements CalendarService {

	@Override
	public void createCalendar(CalendarRequest calendarRequest) {
		validate(calendarRequest);
		validateStringIsObjectId(calendarRequest.getAssigneeId());

		Calendar newCalendar = new Calendar(new ObjectId(), calendarRequest.getYear(),
				calendarRequest.getMonth(), new ObjectId(calendarRequest.getAssigneeId()),
				calendarRequest.getAssignments().stream().map(assignmentRequest -> {
					validateStringIsObjectId(assignmentRequest.getShiftId());

					return new Assignment(assignmentRequest.getDay(),
							new ObjectId(assignmentRequest.getShiftId()));
				}).collect(Collectors.toList()));
		repository.insertAndUpdate(newCalendar);
	}

	@Override
	public Optional<CalendarResponse> getCalendarById(String calendarId) {
		validateStringIsObjectId(calendarId);

		Calendar calendar = repository
				.getCalendars(Map.ofEntries(Map.entry("_id", calendarId)), "", 0, 0, "")
				.orElseThrow(
						() -> new ResourceNotFoundException(LanguageMessageKey.CALENDAR_NOT_FOUND))
				.get(0);

		return Optional
				.of(new CalendarResponse(calendar.get_id().toString(), calendar.getYear(),
						calendar.getMonth(), calendar.getAssigneeId().toString(),
						calendar.getAssignments().stream()
								.map(assignment -> new AssignmentResponse(assignment.getDay(),
										assignment.getShiftId().toString()))
								.collect(Collectors.toList())));
	}

	@Override
	public Optional<ListWrapperResponse<CalendarResponse>> getCalendarByUserId(String userId,
			String keySort, int page, int pageSize, String sortField) {
		validateStringIsObjectId(userId);

		Map<String, String> allParams = new HashMap<>();
		allParams.put("assigneeId", userId);
		List<Calendar> calendars =
				repository.getCalendars(allParams, keySort, 0, 0, sortField).get();

		return Optional.of(new ListWrapperResponse<CalendarResponse>(
				calendars.stream().map(calendar -> new CalendarResponse(
						calendar.get_id().toString(), calendar.getYear(), calendar.getMonth(),
						calendar.getAssigneeId().toString(),
						calendar.getAssignments().stream()
								.map(assignment -> new AssignmentResponse(assignment.getDay(),
										assignment.getShiftId().toString()))
								.collect(Collectors.toList())))
						.collect(Collectors.toList()),
				page, pageSize, repository.getTotalPage(allParams)));
	}

}
