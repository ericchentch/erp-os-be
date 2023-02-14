package com.chilleric.franchise_sys.config;

import static java.util.Map.entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.chilleric.franchise_sys.constant.DefaultValue;
import com.chilleric.franchise_sys.repository.systemRepository.language.Language;
import com.chilleric.franchise_sys.repository.systemRepository.language.LanguageRepository;
import com.chilleric.franchise_sys.repository.systemRepository.permission.Permission;
import com.chilleric.franchise_sys.repository.systemRepository.permission.PermissionRepository;
import com.chilleric.franchise_sys.repository.systemRepository.user.User;
import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import com.chilleric.franchise_sys.repository.systemRepository.user.UserRepository;
import com.chilleric.franchise_sys.utils.DateFormat;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  protected PermissionRepository permissionRepository;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected LanguageRepository languageRepository;

  @Value("${spring.mail.username}")
  protected String email;

  @Value("${default.password}")
  protected String defaultPassword;

  @Value("${server.channel}")
  protected String serverChannel;

  protected final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // ApplicationContext applicationContext = event.getApplicationContext();
    // List<String> paths = new ArrayList<>();
    // applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
    // .forEach((key, value) -> {
    //
    // });
    List<User> userDevs = userRepository
        .getListOrEntity(Map.ofEntries(entry("username", "super_admin_dev")), "", 0, 0, "").get();
    User usersDev = new User();
    if (userDevs.size() == 0) {
      usersDev = new User(new ObjectId(), TypeAccount.INTERNAL, "super_admin_dev",
          bCryptPasswordEncoder
              .encode(Base64.getEncoder().encodeToString(defaultPassword.getBytes())),
          0, "", "", "Dev", "Admin", email, "", new ArrayList<>(), DateFormat.getCurrentTime(),
          null, true, false, 0, DefaultValue.DEFAULT_AVATAR, new ObjectId(), serverChannel,
          new ObjectId());
      userRepository.insertAndUpdate(usersDev);
    } else {
      usersDev = userDevs.get(0);
    }
    List<Permission> permissions = permissionRepository
        .getListOrEntity(Map.ofEntries(entry("name", "super_admin_permission")), "", 0, 0, "")
        .get();
    if (permissions.size() == 0) {
      List<ObjectId> userIds = Arrays.asList(usersDev.get_id());
      Permission permission = new Permission(null, "super_admin_permission", userIds,
          DateFormat.getCurrentTime(), null, permissionRepository.getViewPointSelect(),
          permissionRepository.getEditableSelect(), true);
      permissionRepository.insertAndUpdate(permission);
    } else {
      Permission permission = permissions.get(0);
      permission.setViewPoints(permissionRepository.getViewPointSelect());
      permission.setEditable(permissionRepository.getEditableSelect());
      permissionRepository.insertAndUpdate(permission);
    }
    List<Language> defLanguages =
        languageRepository.getListOrEntity(Map.ofEntries(entry("key", "en")), "", 0, 0, "").get();
    if (defLanguages.size() == 0) {
      Language defLanguage = new Language(null, "English", "en", new HashMap<>());
      languageRepository.insertAndUpdate(defLanguage);
    }
  }
}
