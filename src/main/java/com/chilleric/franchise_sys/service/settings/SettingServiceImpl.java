package com.chilleric.franchise_sys.service.settings;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.settings.AccountSetting;
import com.chilleric.franchise_sys.dto.settings.ChangePasswordRequest;
import com.chilleric.franchise_sys.dto.settings.SettingsRequest;
import com.chilleric.franchise_sys.dto.settings.SettingsResponse;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.inventory.langauge.LanguageInventory;
import com.chilleric.franchise_sys.inventory.user.UserInventory;
import com.chilleric.franchise_sys.repository.settings.Setting;
import com.chilleric.franchise_sys.repository.settings.SettingRepository;
import com.chilleric.franchise_sys.repository.user.User;
import com.chilleric.franchise_sys.repository.user.UserRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.utils.DateFormat;
import com.chilleric.franchise_sys.utils.PasswordValidator;

@Service
public class SettingServiceImpl extends AbstractService<SettingRepository>
        implements SettingService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private LanguageInventory languageInventory;

    @Autowired
    private UserInventory userInventory;

    @Override
    public Optional<SettingsResponse> getSettingsByUserId(String userId) {
        List<Setting> settings =
                repository.getSettings(Map.ofEntries(entry("userId", userId)), "", 0, 0, "").get();

        if (settings.size() == 0) {
            repository.insertAndUpdate(new Setting(null, new ObjectId(userId), false, "en"));
            return Optional.of(new SettingsResponse(false, "en"));
        }
        Setting setting = settings.get(0);
        return Optional.of(objectMapper.convertValue(setting, SettingsResponse.class));
    }

    @Override
    public void updateSettings(SettingsRequest settingsRequest, String userId) {
        validate(settingsRequest);
        Map<String, String> error = generateError(SettingsRequest.class);
        languageInventory.findLanguageByKey(settingsRequest.getLanguageKey()).orElseThrow(() -> {
            error.put("languageKey", LanguageMessageKey.INVALID_LANGUAGE_KEY);
            throw new InvalidRequestException(error, LanguageMessageKey.INVALID_LANGUAGE_KEY);
        });
        List<Setting> settings =
                repository.getSettings(Map.ofEntries(entry("userId", userId)), "", 0, 0, "").get();
        if (settings.size() == 0) {
            repository.insertAndUpdate(
                    new Setting(null, new ObjectId(userId), settingsRequest.isDarkTheme(), "en"));
        } else {
            Setting setting = settings.get(0);
            setting.setDarkTheme(settingsRequest.isDarkTheme());
            setting.setLanguageKey(settingsRequest.getLanguageKey());
            repository.insertAndUpdate(setting);
        }

    }

    @Override
    public void updatePassword(ChangePasswordRequest changePasswordRequest, String userId) {
        validate(changePasswordRequest);
        PasswordValidator.validatePassword(generateError(ChangePasswordRequest.class),
                changePasswordRequest.getOldPassword(), "oldPassword");
        User user = userInventory.findUserById(userId)
                .orElseThrow(() -> new ResourceAccessException(LanguageMessageKey.NOT_FOUND_USER));
        Map<String, String> error = generateError(ChangePasswordRequest.class);
        if (!bCryptPasswordEncoder().matches(changePasswordRequest.getOldPassword(),
                user.getPassword())) {
            error.put("oldPassword", LanguageMessageKey.OLD_PASSWORD_NOT_MATCH);
            throw new InvalidRequestException(error, LanguageMessageKey.OLD_PASSWORD_NOT_MATCH);
        }
        PasswordValidator.validateNewPassword(generateError(ChangePasswordRequest.class),
                changePasswordRequest.getNewPassword(), "newPassword");
        if (changePasswordRequest.getNewPassword()
                .compareTo(changePasswordRequest.getConfirmNewPassword()) != 0) {
            error.put("newPassword", LanguageMessageKey.CONFIRM_PASSWORD_NOT_MATCH);
            error.put("confirmPassword", LanguageMessageKey.CONFIRM_PASSWORD_NOT_MATCH);
            throw new InvalidRequestException(error, LanguageMessageKey.CONFIRM_PASSWORD_NOT_MATCH);
        }
        user.setModified(DateFormat.getCurrentTime());
        user.setPassword(bCryptPasswordEncoder().encode(changePasswordRequest.getNewPassword()));
        userRepository.insertAndUpdate(user);
    }

    @Override
    public void updateAccountInformation(AccountSetting accountSetting, String userId) {
        validate(accountSetting);
        Map<String, String> error = generateError(AccountSetting.class);
        User user = userInventory.findUserById(userId)
                .orElseThrow(() -> new ResourceAccessException(LanguageMessageKey.NOT_FOUND_EMAIL));
        userInventory.findUserByEmail(accountSetting.getEmail()).ifPresent(thisEmail -> {
            if (thisEmail.get_id().compareTo(user.get_id()) != 0) {
                error.put("email", LanguageMessageKey.EMAIL_TAKEN);
                throw new InvalidRequestException(error, LanguageMessageKey.EMAIL_TAKEN);
            }
        });
        userInventory.findUserByPhone(accountSetting.getPhone()).ifPresent(thisPhone -> {
            if (thisPhone.get_id().compareTo((user.get_id())) != 0) {
                error.put("phone", LanguageMessageKey.PHONE_TAKEN);
                throw new InvalidRequestException(error, LanguageMessageKey.PHONE_TAKEN);
            }
        });
        user.setPhone(accountSetting.getPhone());
        user.setEmail(accountSetting.getEmail());
        user.setUsername(accountSetting.getUsername());
        user.setFirstName(accountSetting.getFirstName());
        user.setLastName(accountSetting.getLastName());
        user.setVerify2FA(accountSetting.isVerify2FA());
        user.setGender(accountSetting.getGender());
        user.setDob(accountSetting.getDob());
        user.setAddress(accountSetting.getAddress());
        userRepository.insertAndUpdate(user);
    }

}
