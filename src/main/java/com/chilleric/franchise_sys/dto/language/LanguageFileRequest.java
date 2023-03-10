package com.chilleric.franchise_sys.dto.language;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageFileRequest {
  @NotNull(message = LanguageMessageKey.ID_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.ID_REQUIRED)
  @NotBlank(message = LanguageMessageKey.ID_REQUIRED)
  private String id;

  private Map<String, String> dictionary;
}
