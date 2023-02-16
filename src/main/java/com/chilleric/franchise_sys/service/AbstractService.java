package com.chilleric.franchise_sys.service;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.constant.ResponseType;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.ForbiddenException;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.log.AppLogger;
import com.chilleric.franchise_sys.log.LoggerFactory;
import com.chilleric.franchise_sys.log.LoggerType;
import com.chilleric.franchise_sys.pusher.PusherService;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import com.chilleric.franchise_sys.repository.system_repository.accessability.AccessabilityRepository;
import com.chilleric.franchise_sys.repository.system_repository.user.UserRepository;
import com.chilleric.franchise_sys.utils.ObjectValidator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class AbstractService<r> {
  @Autowired
  protected r repository;

  @Autowired
  protected Environment env;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected PusherService pusherService;

  @Autowired
  protected ObjectValidator objectValidator;

  protected ObjectMapper objectMapper;

  protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

  @Autowired
  protected AccessabilityRepository accessabilityRepository;

  @PostConstruct
  public void init() {
    objectMapper =
      new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  protected String generateParamsValue(List<String> list) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      result.append(list.get(i));
      if (i != list.size() - 1) {
        result.append(",");
      }
    }
    return result.toString();
  }

  protected BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  protected <T> void validate(T request) {
    boolean isError = false;
    Map<String, String> errors = objectValidator.validateRequestThenReturnMessage(
      generateError(request.getClass()),
      request
    );
    for (Map.Entry<String, String> items : errors.entrySet()) {
      if (items.getValue().length() > 0) {
        isError = true;
        break;
      }
    }
    if (isError) {
      throw new InvalidRequestException(errors, LanguageMessageKey.INVALID_REQUEST);
    }
  }

  protected String getNotiId(String loginId) {
    return userRepository
      .getEntityByAttribute(loginId, "_id")
      .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR))
      .getNotificationId()
      .toString();
  }

  protected void validateStringIsObjectId(String id) {
    if (!ObjectId.isValid(id)) {
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }
  }

  protected <T> T viewPointToRequest(
    T request,
    List<ViewPoint> viewPoints,
    Object source
  ) {
    for (Field field : request.getClass().getDeclaredFields()) {
      boolean isEditable = false;
      field.setAccessible(true);
      for (ViewPoint thisView : viewPoints) {
        if (thisView.getKey().compareTo(field.getName()) == 0) {
          isEditable = true;
          break;
        }
      }
      if (!isEditable) {
        for (Field field1 : source.getClass().getDeclaredFields()) {
          field1.setAccessible(true);
          if (field1.getName().compareTo(field.getName()) == 0) {
            try {
              field.set(request, field1.get(source));
            } catch (IllegalArgumentException | IllegalAccessException e) {
              APP_LOGGER.error(e.getMessage());
              throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
            }
          }
        }
      }
    }
    return request;
  }

  protected Map<String, String> generateError(Class<?> clazz) {
    Field[] fields = clazz.getDeclaredFields();
    Map<String, String> result = new HashMap<>();
    for (Field field : fields) {
      result.put(field.getName(), "");
    }
    return result;
  }

  protected ResponseType isPublic(
    String ownerId,
    String loginId,
    boolean skipAccessability
  ) {
    if (skipAccessability) {
      return ResponseType.PRIVATE;
    }
    if (ownerId.compareTo(loginId) == 0) {
      return ResponseType.PRIVATE;
    } else {
      return ResponseType.PUBLIC;
    }
  }

  protected void checkDeleteAndEdit(String userId, String targetId) {
    accessabilityRepository
      .getAccessability(userId, targetId)
      .orElseThrow(() -> new ForbiddenException(LanguageMessageKey.FORBIDDEN));
  }
}
