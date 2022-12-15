package com.chilleric.franchise_sys.service.settings;

import java.util.Optional;
import com.chilleric.franchise_sys.dto.settings.AccountSetting;
import com.chilleric.franchise_sys.dto.settings.ChangePasswordRequest;
import com.chilleric.franchise_sys.dto.settings.SettingsRequest;
import com.chilleric.franchise_sys.dto.settings.SettingsResponse;

public interface SettingService {
    Optional<SettingsResponse> getSettingsByUserId(String userId);

    void updateSettings(SettingsRequest settingsRequest, String userId);

    void updatePassword(ChangePasswordRequest changePasswordRequest, String userId);

    void updateAccountInformation(AccountSetting accountSetting, String userId);
}
