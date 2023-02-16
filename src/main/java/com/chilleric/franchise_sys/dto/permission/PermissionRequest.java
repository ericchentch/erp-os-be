package com.chilleric.franchise_sys.dto.permission;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {
  @NotBlank(message = LanguageMessageKey.PERMISSION_NAME_REQUIRED)
  @NotNull(message = LanguageMessageKey.PERMISSION_NAME_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.PERMISSION_NAME_REQUIRED)
  private String name;

  @NotNull(message = LanguageMessageKey.USER_LIST_REQUIRED)
  private List<String> userId;

  @NotNull(message = LanguageMessageKey.VIEW_POINT_REQUIRED)
  private Map<String, List<ViewPoint>> viewPoints;

  @NotNull(message = LanguageMessageKey.VIEW_POINT_REQUIRED)
  private Map<String, List<ViewPoint>> editable;

  @NotNull(message = LanguageMessageKey.IS_SERVER_REQURIED)
  @Min(value = 0, message = LanguageMessageKey.ONLY_0_1)
  @Max(value = 1, message = LanguageMessageKey.ONLY_0_1)
  private int isServer;
}
