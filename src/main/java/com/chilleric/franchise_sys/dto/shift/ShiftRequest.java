package com.chilleric.franchise_sys.dto.shift;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftRequest {
    @NotNull(message = LanguageMessageKey.SHIFT_NAME_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SHIFT_NAME_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SHIFT_NAME_REQUIRED)
    private String shiftName;

    @NotNull(message = LanguageMessageKey.SHIFT_START_DATE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SHIFT_START_DATE_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SHIFT_START_DATE_REQUIRED)
    private Date startDate;

    @NotNull(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    private Date endDate;
}
