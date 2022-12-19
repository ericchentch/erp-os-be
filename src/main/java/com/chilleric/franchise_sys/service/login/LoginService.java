package com.chilleric.franchise_sys.service.login;

import java.util.Optional;
import com.chilleric.franchise_sys.dto.login.LoginRequest;
import com.chilleric.franchise_sys.dto.login.LoginResponse;
import com.chilleric.franchise_sys.dto.login.RegisterRequest;

public interface LoginService {
    Optional<LoginResponse> login(LoginRequest loginRequest, boolean isRegister);

    void logout(String id);

    void register(RegisterRequest registerRequest);

    void verifyRegister(String code, String email);

    void resendVerifyRegister(String email);

    void forgotPassword(String email);

    Optional<LoginResponse> verify2FA(String email, String code);

    void resend2FACode(String email);
}
