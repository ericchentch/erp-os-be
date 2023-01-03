package com.chilleric.franchise_sys.dto.calendar;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRequest {
    @NotNull(message = LanguageMessageKey.CALENDAR_YEAR_REQUIRED)
    @Min(2000)
    @Max(2999)
    private int year;

    @NotNull(message = LanguageMessageKey.CALENDAR_MONTH_REQUIRED)
    @Min(1)
    @Max(12)
    private int month;

    @NotNull(message = LanguageMessageKey.CALENDAR_ASSIGNEE_ID_REQUIRED)
    @NotBlank(message = LanguageMessageKey.CALENDAR_ASSIGNEE_ID_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.CALENDAR_ASSIGNEE_ID_REQUIRED)
    private String assigneeId;

    @NotNull(message = LanguageMessageKey.CALENDAR_ASSIGNMENT_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.CALENDAR_ASSIGNMENT_REQUIRED)
    private List<AssignmentRequest> assignments;
}
