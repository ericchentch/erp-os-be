package com.chilleric.franchise_sys.dto.calendar;

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
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest {
    @NotNull(message = LanguageMessageKey.CALENDAR_DAY_REQUIRED)
    @Min(1)
    @Max(31)
    private int day;

    @NotNull(message = LanguageMessageKey.CALENDAR_SHIFT_ID_REQUIRED)
    @NotBlank(message = LanguageMessageKey.CALENDAR_SHIFT_ID_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.CALENDAR_SHIFT_ID_REQUIRED)
    private String shiftId;
}
