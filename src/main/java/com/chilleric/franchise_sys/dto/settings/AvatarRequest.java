package com.chilleric.franchise_sys.dto.settings;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarRequest {
  @NotNull(message = LanguageMessageKey.AVATAR_REQUIRED)
  private String avatar;
}
