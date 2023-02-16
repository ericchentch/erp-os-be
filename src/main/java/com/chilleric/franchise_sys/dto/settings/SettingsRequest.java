package com.chilleric.franchise_sys.dto.settings;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsRequest {
  @NotEmpty(message = LanguageMessageKey.LANGUAGE_KEY_REQUIRED)
  @NotNull(message = LanguageMessageKey.LANGUAGE_KEY_REQUIRED)
  @NotBlank(message = LanguageMessageKey.LANGUAGE_KEY_REQUIRED)
  private String languageKey;
}
