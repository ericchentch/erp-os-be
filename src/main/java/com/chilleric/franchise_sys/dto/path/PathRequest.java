package com.chilleric.franchise_sys.dto.path;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.repository.user.User.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathRequest {
    @NotEmpty(message = LanguageMessageKey.LABEL_REQUIRED)
    @NotBlank(message = LanguageMessageKey.LABEL_REQUIRED)
    private String label;

    @NotEmpty(message = LanguageMessageKey.PATH_REQUIRED)
    @NotBlank(message = LanguageMessageKey.PATH_REQUIRED)
    private String path;

    @NotEmpty(message = LanguageMessageKey.TYPE_PATH_REQUIRED)
    @NotBlank(message = LanguageMessageKey.TYPE_PATH_REQUIRED)
    private TypeAccount type;

}
