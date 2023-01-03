package com.chilleric.franchise_sys.dto.login;

import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userId;
    private String token;
    private TypeAccount type;
    private boolean isVerify2Fa;
    private boolean needVerify;
}
