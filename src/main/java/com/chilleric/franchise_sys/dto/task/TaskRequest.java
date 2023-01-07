package com.chilleric.franchise_sys.dto.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.chilleric.franchise_sys.annotation.IsObjectId;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.constant.TypeValidation;
import com.chilleric.franchise_sys.repository.informationRepository.task.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank(message = LanguageMessageKey.TASK_EMPLOYEE_ID_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_EMPLOYEE_ID_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_EMPLOYEE_ID_REQUIRED)
    @IsObjectId(message = LanguageMessageKey.INVALID_OBJECT_ID)
    private String employeeId;

    @NotBlank(message = LanguageMessageKey.TASK_SHIFT_ID_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_SHIFT_ID_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_SHIFT_ID_REQUIRED)
    @IsObjectId(message = LanguageMessageKey.INVALID_OBJECT_ID)
    private String shiftId;

    @NotBlank(message = LanguageMessageKey.TASK_REVIEWER_ID_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_REVIEWER_ID_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_REVIEWER_ID_REQUIRED)
    @IsObjectId(message = LanguageMessageKey.INVALID_OBJECT_ID)
    private String reviewerId;

    @NotBlank(message = LanguageMessageKey.TASK_START_DATE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_START_DATE_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_START_DATE_REQUIRED)
    @Pattern(regexp = TypeValidation.DATE, message = LanguageMessageKey.SHIFT_INVALID_START_DATE)
    private String startDate;

    @NotBlank(message = LanguageMessageKey.TASK_START_HOUR_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_START_HOUR_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_START_HOUR_REQUIRED)
    @Pattern(regexp = TypeValidation.HOUR, message = LanguageMessageKey.SHIFT_INVALID_START_DATE)
    private String startHour;


    @NotBlank(message = LanguageMessageKey.TASK_TITLE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_TITLE_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_TITLE_REQUIRED)
    private String title;

    @NotBlank(message = LanguageMessageKey.TASK_DESCRIPTION_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.TASK_DESCRIPTION_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_DESCRIPTION_REQUIRED)
    private String description;

    // @NotBlank(message = LanguageMessageKey.TASK_STATUS_REQUIRED)
    // @NotEmpty(message = LanguageMessageKey.TASK_STATUS_REQUIRED)
    @NotNull(message = LanguageMessageKey.TASK_STATUS_REQUIRED)
    private TaskStatus status;

}
