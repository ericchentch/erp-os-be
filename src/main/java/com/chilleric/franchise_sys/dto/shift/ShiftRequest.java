package com.chilleric.franchise_sys.dto.shift;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.constant.TypeValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = TypeValidation.DATE, message = LanguageMessageKey.INVALID_START_DATE)
    private String startDate;

    @NotNull(message = LanguageMessageKey.SHIFT_START_HOUR_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SHIFT_START_HOUR_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SHIFT_START_HOUR_REQUIRED)
    @Pattern(regexp = TypeValidation.DATE, message = LanguageMessageKey.INVALID_START_HOUR)
    private String startHour;

    @NotNull(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SHIFT_END_DATE_REQUIRED)
    @Pattern(regexp = TypeValidation.DATE, message = LanguageMessageKey.INVALID_END_DATE)
    private String endDate;

  @NotNull(message = LanguageMessageKey.SHIFT_END_HOUR_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.SHIFT_END_HOUR_REQUIRED)
  @NotBlank(message = LanguageMessageKey.SHIFT_END_HOUR_REQUIRED)
  @Pattern(regexp = TypeValidation.DATE, message = LanguageMessageKey.INVALID_END_HOUR)
  private String endHour;
}
