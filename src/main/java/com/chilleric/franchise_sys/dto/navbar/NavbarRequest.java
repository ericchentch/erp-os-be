package com.chilleric.franchise_sys.dto.navbar;

import java.util.List;
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
public class NavbarRequest {

  @NotBlank(message = LanguageMessageKey.NAVBAR_NAME_REQUIRED)
  @NotNull(message = LanguageMessageKey.NAVBAR_NAME_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.NAVBAR_NAME_REQUIRED)
  private String name;

  @NotNull(message = LanguageMessageKey.USER_LIST_REQUIRED)
  private List<String> userIds;

  @NotNull(message = LanguageMessageKey.CONTENT_NAVBAR_REQUIRED)
  private List<ContentNavbarRequest> content;
}
