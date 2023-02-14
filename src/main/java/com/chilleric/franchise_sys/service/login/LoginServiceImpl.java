package com.chilleric.franchise_sys.service.login;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.DefaultValue;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.constant.TypeValidation;
import com.chilleric.franchise_sys.dto.login.LoginRequest;
import com.chilleric.franchise_sys.dto.login.LoginResponse;
import com.chilleric.franchise_sys.email.EmailDetail;
import com.chilleric.franchise_sys.email.EmailService;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.exception.UnauthorizedException;
import com.chilleric.franchise_sys.jwt.GoogleValidation;
import com.chilleric.franchise_sys.jwt.JwtValidation;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.code.Code;
import com.chilleric.franchise_sys.repository.systemRepository.code.CodeRepository;
import com.chilleric.franchise_sys.repository.systemRepository.code.TypeCode;
import com.chilleric.franchise_sys.repository.systemRepository.user.User;
import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import com.chilleric.franchise_sys.repository.systemRepository.user.UserRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.utils.PasswordValidator;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

@Service
public class LoginServiceImpl extends AbstractService<UserRepository> implements LoginService {

  @Value("${default.password}")
  protected String defaultPassword;

  @Autowired
  private EmailService emailService;

  @Autowired
  private CodeRepository codeRepository;

  @Autowired
  private GoogleValidation googleValidation;

  @Autowired
  private JwtValidation jwtValidation;

  @Override
  public Optional<LoginResponse> login(LoginRequest loginRequest, boolean isRegister) {
    validate(loginRequest);
    User user = new User();
    boolean normalUsername = true;
    if (loginRequest.getUsername().matches(TypeValidation.EMAIL)) {
      user = repository.getEntityByAttribute(loginRequest.getUsername(), "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
      normalUsername = false;
    }
    if (loginRequest.getUsername().matches(TypeValidation.PHONE)) {
      user = repository.getEntityByAttribute(loginRequest.getUsername(), "phone").orElseThrow(
          () -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_PHONE_NUMBER));
      normalUsername = false;
    }
    if (normalUsername) {
      user = repository.getEntityByAttribute(loginRequest.getUsername(), "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USERNAME));
    }
    Map<String, String> error = generateError(LoginRequest.class);
    PasswordValidator.validatePassword(generateError(LoginRequest.class),
        loginRequest.getPassword(), "password");
    if (user.getDeleted() == 1) {
      throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
    }
    if (!user.isVerified()) {
      String newCode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
      Date now = new Date();
      Date expiredDate = new Date(now.getTime() + 5 * 60 * 1000L);
      Optional<Code> codes =
          codeRepository.getCodesByType(user.get_id().toString(), TypeCode.REGISTER.name());
      if (codes.isPresent()) {
        Code code = codes.get();
        code.setCode(newCode);
        code.setExpiredDate(expiredDate);
        codeRepository.insertAndUpdate(code);
      } else {
        Code code = new Code(null, user.get_id(), TypeCode.REGISTER, newCode, expiredDate);
        codeRepository.insertAndUpdate(code);
      }
      emailService.sendSimpleMail(new EmailDetail(user.getEmail(), newCode, "OTP"));
      return Optional.of(new LoginResponse("", "", TypeAccount.EXTERNAL, false, true));
    }
    if (!bCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
      error.put("password", LanguageMessageKey.PASSWORD_NOT_MATCH);
      throw new InvalidRequestException(error, LanguageMessageKey.PASSWORD_NOT_MATCH);
    }
    Date now = new Date();
    if (user.isVerify2FA()) {
      String verify2FACode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
      emailService.sendSimpleMail(new EmailDetail(user.getEmail(), verify2FACode, "OTP"));
      Date expiredDate = new Date(now.getTime() + 5 * 60 * 1000L);
      Optional<Code> codes =
          codeRepository.getCodesByType(user.get_id().toString(), TypeCode.VERIFY2FA.name());
      if (codes.isPresent()) {
        Code code = codes.get();
        code.setCode(verify2FACode);
        code.setExpiredDate(expiredDate);
        codeRepository.insertAndUpdate(code);
      } else {
        Code code = new Code(null, user.get_id(), TypeCode.VERIFY2FA, verify2FACode, expiredDate);
        codeRepository.insertAndUpdate(code);
      }
      return Optional.of(new LoginResponse("", "", TypeAccount.EXTERNAL, true, false));
    } else {
      String newTokens = jwtValidation.generateToken(user.get_id().toString());
      List<String> token = user.getTokens();
      if (user.getTokens().size() == 10) {
        token.remove(9);
        token.add(0, newTokens);
      } else {
        token.add(newTokens);
      }
      user.setTokens(token);
      repository.insertAndUpdate(user);
      pusherService.sendNotification("New login", "Someone login your account!",
          Arrays.asList(user.getNotificationId().toString()));
      return Optional.of(new LoginResponse(user.get_id().toString(), "Bearer " + newTokens,
          user.getType(), false, false));
    }
  }

  @Override
  public void logout(String id, String logoutToken) {
    User user = userRepository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USER));
    List<String> token = user.getTokens();
    token.remove(logoutToken);
    user.setTokens(token);
    repository.insertAndUpdate(user);
  }

  // @Override
  // public void register(RegisterRequest registerRequest) {
  // validate(registerRequest);
  // Map<String, String> error = generateError(RegisterRequest.class);
  // userInventory.findUserByUsername(registerRequest.getUsername()).ifPresent(username -> {
  // error.put("username", LanguageMessageKey.USERNAME_EXISTED);
  // throw new InvalidRequestException(error, LanguageMessageKey.USERNAME_EXISTED);
  // });
  // userInventory.findUserByEmail(registerRequest.getEmail()).ifPresent(userEmail -> {
  // if (userEmail.isVerified()) {
  // error.put("email", LanguageMessageKey.EMAIL_TAKEN);
  // throw new InvalidRequestException(error, LanguageMessageKey.EMAIL_TAKEN);
  // } else {
  // error.put("email", LanguageMessageKey.PLEASE_VERIFY_EMAIL);
  // throw new InvalidRequestException(error, LanguageMessageKey.PLEASE_VERIFY_EMAIL);
  // }
  // });
  // userInventory.findUserByPhone(registerRequest.getPhone()).ifPresent(userPhone -> {
  // error.put("phone", LanguageMessageKey.PHONE_TAKEN);
  // throw new InvalidRequestException(error, LanguageMessageKey.PHONE_TAKEN);
  // });
  // PasswordValidator.validateNewPassword(error, registerRequest.getPassword(), "password");
  // String passwordEncode = bCryptPasswordEncoder().encode(registerRequest.getPassword());
  // User user = objectMapper.convertValue(registerRequest, User.class);
  // ObjectId newId = new ObjectId();
  // user.set_id(newId);
  // user.setPassword(passwordEncode);
  // user.setTokens("");
  // user.setGender(0);
  // user.setType(TypeAccount.EXTERNAL);
  // user.setDob("");
  // repository.insertAndUpdate(user);
  // String newCode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
  // Date now = new Date();
  // Date expiredDate = new Date(now.getTime() + 5 * 60 * 1000L);
  // Optional<Code> codes =
  // codeRepository.getCodesByType(user.get_id().toString(), TypeCode.REGISTER.name());
  // if (codes.isPresent()) {
  // Code code = codes.get();
  // code.setCode(newCode);
  // code.setExpiredDate(expiredDate);
  // codeRepository.insertAndUpdateCode(code);
  // } else {
  // Code code = new Code(null, user.get_id(), TypeCode.REGISTER, newCode, expiredDate);
  // codeRepository.insertAndUpdateCode(code);
  // }
  // emailService.sendSimpleMail(new EmailDetail(user.getEmail(), newCode, "OTP"));

  // }

  @Override
  public void verifyRegister(String inputCode, String email) {
    User user = new User();
    boolean normalUsername = true;
    if (email.matches(TypeValidation.EMAIL)) {
      user = repository.getEntityByAttribute(email, "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
      normalUsername = false;
    }
    if (email.matches(TypeValidation.PHONE)) {
      user = repository.getEntityByAttribute(email, "phone").orElseThrow(
          () -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_PHONE_NUMBER));
      normalUsername = false;
    }
    if (normalUsername) {
      user = repository.getEntityByAttribute(email, "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USERNAME));
    }
    Date now = new Date();
    Optional<Code> codes =
        codeRepository.getCodesByType(user.get_id().toString(), TypeCode.REGISTER.name());
    if (codes.isPresent()) {
      Code code = codes.get();
      if (code.getCode().compareTo(inputCode) != 0) {
        throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.INVALID_CODE);
      } else if (code.getExpiredDate().compareTo(now) < 0) {
        throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.CODE_EXPIRED);
      }
    } else {
      throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.INVALID_CODE);
    }
    user.setVerified(true);
    repository.insertAndUpdate(user);
  }

  @Override
  public void resendVerifyRegister(String email) {
    boolean normalUsername = true;
    User userCheckMail = new User();
    if (email.matches(TypeValidation.EMAIL)) {
      userCheckMail = repository.getEntityByAttribute(email, "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
      normalUsername = false;
    }
    if (email.matches(TypeValidation.PHONE)) {
      userCheckMail = repository.getEntityByAttribute(email, "phone").orElseThrow(
          () -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_PHONE_NUMBER));
      normalUsername = false;
    }
    if (normalUsername) {
      userCheckMail = repository.getEntityByAttribute(email, "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USERNAME));
    }
    String newCode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + 5 * 60 * 1000L);
    Optional<Code> codes =
        codeRepository.getCodesByType(userCheckMail.get_id().toString(), TypeCode.REGISTER.name());
    if (codes.isPresent()) {
      Code code = codes.get();
      code.setCode(newCode);
      code.setExpiredDate(expiredDate);
      codeRepository.insertAndUpdate(code);
    } else {
      Code code = new Code(null, userCheckMail.get_id(), TypeCode.REGISTER, newCode, expiredDate);
      codeRepository.insertAndUpdate(code);
    }
    emailService.sendSimpleMail(new EmailDetail(userCheckMail.getEmail(), newCode, "OTP"));

  }

  @Override
  public void forgotPassword(String email) {
    User user = new User();
    boolean normalUsername = true;
    if (email.matches(TypeValidation.EMAIL)) {
      user = repository.getEntityByAttribute(email, "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
      normalUsername = false;
    }
    if (email.matches(TypeValidation.PHONE)) {
      user = repository.getEntityByAttribute(email, "phone").orElseThrow(
          () -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_PHONE_NUMBER));
      normalUsername = false;
    }
    if (normalUsername) {
      user = repository.getEntityByAttribute(email, "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USERNAME));
    }
    user.setPassword(bCryptPasswordEncoder()
        .encode(Base64.getEncoder().encodeToString(defaultPassword.getBytes())));
    repository.insertAndUpdate(user);
    emailService.sendSimpleMail(new EmailDetail(user.getEmail(),
        "Username: " + user.getUsername() + " \n" + "Password: " + defaultPassword,
        "New password!"));
  }

  @Override
  public Optional<LoginResponse> verify2FA(String email, String inputCode) {
    User user = new User();
    if (email.matches(TypeValidation.EMAIL)) {
      user = repository.getEntityByAttribute(email, "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    } else if (email.matches(TypeValidation.PHONE)) {
      user = repository.getEntityByAttribute(email, "phone")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    } else {
      user = repository.getEntityByAttribute(email, "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    }
    Date now = new Date();
    Optional<Code> codes =
        codeRepository.getCodesByType(user.get_id().toString(), TypeCode.VERIFY2FA.name());
    if (codes.isPresent()) {
      Code code = codes.get();
      if (code.getCode().compareTo(inputCode) != 0) {
        throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.INVALID_CODE);
      } else if (code.getExpiredDate().compareTo(now) < 0) {
        throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.CODE_EXPIRED);
      }
    } else {
      throw new InvalidRequestException(new HashMap<>(), LanguageMessageKey.INVALID_CODE);
    }
    String newTokens = jwtValidation.generateToken(user.get_id().toString());
    List<String> token = user.getTokens();
    if (user.getTokens().size() == 10) {
      token.remove(9);
      token.add(0, newTokens);
    } else {
      token.add(newTokens);
    }
    user.setTokens(token);
    repository.insertAndUpdate(user);
    pusherService.sendNotification("New login", "Someone login your account!",
        Arrays.asList(user.getNotificationId().toString()));
    return Optional.of(new LoginResponse(user.get_id().toString(), "Bearer " + newTokens,
        user.getType(), false, false));

  }

  @Override
  public void resend2FACode(String email) {
    User user = new User();
    if (email.matches(TypeValidation.EMAIL)) {
      user = repository.getEntityByAttribute(email, "email")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    } else if (email.matches(TypeValidation.PHONE)) {
      user = repository.getEntityByAttribute(email, "phone")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    } else {
      user = repository.getEntityByAttribute(email, "username")
          .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_EMAIL));
    }
    String newCode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + 5 * 60 * 1000L);
    Optional<Code> codes =
        codeRepository.getCodesByType(user.get_id().toString(), TypeCode.VERIFY2FA.name());
    if (codes.isPresent()) {
      Code code = codes.get();
      code.setCode(newCode);
      code.setExpiredDate(expiredDate);
      codeRepository.insertAndUpdate(code);
    } else {
      Code code = new Code(null, user.get_id(), TypeCode.VERIFY2FA, newCode, expiredDate);
      codeRepository.insertAndUpdate(code);
    }
    emailService.sendSimpleMail(new EmailDetail(user.getEmail(), newCode, "OTP"));
  }

  @Override
  public Optional<LoginResponse> loginGoogle(String token) {
    Payload payload = googleValidation.validateTokenId(token);
    String email = payload.getEmail();
    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
    if (!emailVerified) {
      throw new InvalidRequestException(new HashMap<>(),
          LanguageMessageKey.GG_EMAIL_IS_NOT_VERIFIED);
    }
    // String name = (String) payload.get("name");
    // String userId = payload.getSubject();
    // String pictureUrl = (String) payload.get("picture");
    // String locale = (String) payload.get("locale");
    String familyName = (String) payload.get("family_name");
    String givenName = (String) payload.get("given_name");
    Optional<User> getUser = repository.getEntityByAttribute(email, "email");
    if (getUser.isPresent()) {
      User user = getUser.get();
      String newTokens = jwtValidation.generateToken(user.get_id().toString());
      List<String> tokenUser = user.getTokens();
      if (user.getTokens().size() == 10) {
        tokenUser.remove(9);
        tokenUser.add(0, newTokens);
      } else {
        tokenUser.add(newTokens);
      }
      user.setTokens(tokenUser);
      user.setVerify2FA(false);
      user.setVerified(true);
      pusherService.sendNotification("New login", "Someone login your account!",
          Arrays.asList(user.getNotificationId().toString()));
      repository.insertAndUpdate(user);
      return Optional.of(new LoginResponse(user.get_id().toString(), "Bearer " + newTokens,
          user.getType(), false, false));

    } else {
      Date now = new Date();
      ObjectId newId = new ObjectId();
      String newTokens = jwtValidation.generateToken(newId.toString());
      List<String> tokenUser = new ArrayList<>();
      tokenUser.add(newTokens);
      User user = new User(newId, TypeAccount.EXTERNAL, "",
          bCryptPasswordEncoder()
              .encode(Base64.getEncoder().encodeToString(defaultPassword.getBytes())),
          0, "", "", givenName, familyName, email, "", tokenUser, now, null, emailVerified, false,
          0, DefaultValue.DEFAULT_AVATAR, new ObjectId(), null, new ObjectId());
      User userAdmin = userRepository.getEntityByAttribute("super_admin_dev", "username")
          .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
      accessabilityRepository
          .insertAndUpdate(new Accessability(null, userAdmin.get_id(), newId, true, false));
      repository.insertAndUpdate(user);
      return Optional.of(new LoginResponse(user.get_id().toString(), "Bearer " + newTokens,
          user.getType(), false, false));
    }

  }

}
