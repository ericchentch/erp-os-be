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
public class ChangePasswordRequest {
  @NotEmpty(message = LanguageMessageKey.OLD_PASSWORD_REQUIRED)
  @NotBlank(message = LanguageMessageKey.OLD_PASSWORD_REQUIRED)
  @NotNull(message = LanguageMessageKey.OLD_PASSWORD_REQUIRED)
  private String oldPassword;

  @NotEmpty(message = LanguageMessageKey.NEW_PASSWORD_REQUIRED)
  @NotBlank(message = LanguageMessageKey.NEW_PASSWORD_REQUIRED)
  @NotNull(message = LanguageMessageKey.NEW_PASSWORD_REQUIRED)
  private String newPassword;

  @NotEmpty(message = LanguageMessageKey.CONFIRM_PASSWORD_REQUIRED)
  @NotBlank(message = LanguageMessageKey.CONFIRM_PASSWORD_REQUIRED)
  @NotNull(message = LanguageMessageKey.CONFIRM_PASSWORD_REQUIRED)
  private String confirmNewPassword;
}
