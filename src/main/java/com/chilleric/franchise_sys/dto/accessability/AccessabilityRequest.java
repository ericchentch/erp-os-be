package com.chilleric.franchise_sys.dto.accessability;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessabilityRequest {
  @NotNull(message = LanguageMessageKey.USER_LIST_REQUIRED)
  private List<String> userIds;

  @NotNull(message = LanguageMessageKey.EDITABLE_REQUIRED)
  @Min(value = 0, message = LanguageMessageKey.ONLY_0_1)
  @Max(value = 1, message = LanguageMessageKey.ONLY_0_1)
  private int editable;
}
