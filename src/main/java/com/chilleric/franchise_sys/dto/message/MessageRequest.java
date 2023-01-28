package com.chilleric.franchise_sys.dto.message;

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
public class MessageRequest {

  @NotEmpty(message = LanguageMessageKey.MESSAGE_REQUIRED)
  @NotBlank(message = LanguageMessageKey.MESSAGE_REQUIRED)
  @NotNull(message = LanguageMessageKey.MESSAGE_REQUIRED)
  private String message;
}
