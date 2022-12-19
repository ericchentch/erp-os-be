package com.chilleric.franchise_sys.config;

import static java.util.Map.entry;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.chilleric.franchise_sys.repository.language.Language;
import com.chilleric.franchise_sys.repository.language.LanguageRepository;
import com.chilleric.franchise_sys.repository.path.PathRepository;
import com.chilleric.franchise_sys.repository.permission.Permission;
import com.chilleric.franchise_sys.repository.permission.PermissionRepository;
import com.chilleric.franchise_sys.repository.user.User;
import com.chilleric.franchise_sys.repository.user.User.TypeAccount;
import com.chilleric.franchise_sys.repository.user.UserRepository;
import com.chilleric.franchise_sys.utils.DateFormat;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LanguageRepository languageRepository;

  @Value("${spring.mail.username}")
  protected String email;

  @Value("${default.password}")
  protected String defaultPassword;

  @Autowired
  private PathRepository pathRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // ApplicationContext applicationContext = event.getApplicationContext();
    // List<String> paths = new ArrayList<>();
    // applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
    // .forEach((key, value) -> {
    //
    // });

    List<User> users = userRepository
        .getUsers(Map.ofEntries(entry("username", "super_admin")), "", 0, 0, "").get();
    User user = new User();
    if (users.size() == 0) {
      user = new User(new ObjectId(), null, TypeAccount.INTERNAL, "super_admin",
          bCryptPasswordEncoder
              .encode(Base64.getEncoder().encodeToString(defaultPassword.getBytes())),
          0, "", "", "Super", "Admin", email, "", "", DateFormat.getCurrentTime(), null, true,
          false, 0);
      userRepository.insertAndUpdate(user);
    } else {
      user = users.get(0);
    }
    List<User> userDevs = userRepository
        .getUsers(Map.ofEntries(entry("username", "super_admin_dev")), "", 0, 0, "").get();
    User usersDev = new User();
    if (userDevs.size() == 0) {
      usersDev = new User(new ObjectId(), null, TypeAccount.INTERNAL, "super_admin_dev",
          bCryptPasswordEncoder
              .encode(Base64.getEncoder().encodeToString(defaultPassword.getBytes())),
          0, "", "", "Dev", "Admin", email, "", "", DateFormat.getCurrentTime(), null, true, false,
          0);
      userRepository.insertAndUpdate(usersDev);
    } else {
      usersDev = userDevs.get(0);
    }
    List<Permission> permissions = permissionRepository
        .getPermissions(Map.ofEntries(entry("name", "super_admin_permission")), "", 0, 0, "").get();
    List<ObjectId> paths = pathRepository.getPaths(new HashMap<>(), "", 0, 0, "").get().stream()
        .map(thisPath -> thisPath.get_id()).collect(Collectors.toList());
    if (permissions.size() == 0) {
      List<ObjectId> userIds = Arrays.asList(user.get_id(), usersDev.get_id());
      Permission permission = new Permission(null, "super_admin_permission", userIds, paths,
          DateFormat.getCurrentTime(), null, permissionRepository.getViewPointSelect(),
          permissionRepository.getEditableSelect());
      permissionRepository.insertAndUpdate(permission);
    } else {
      Permission permission = permissions.get(0);
      permission.setViewPoints(permissionRepository.getViewPointSelect());
      permission.setEditable(permissionRepository.getEditableSelect());
      permission.setPaths(paths);
      permissionRepository.insertAndUpdate(permission);
    }
    List<Language> defLanguages =
        languageRepository.getLanguages(Map.ofEntries(entry("key", "en")), "", 0, 0, "").get();
    if (defLanguages.size() == 0) {
      Language defLanguage = new Language(null, "English", "en", new HashMap<>());
      languageRepository.insertAndUpdate(defLanguage);
    }
  }
}
