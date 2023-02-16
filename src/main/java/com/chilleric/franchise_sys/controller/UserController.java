package com.chilleric.franchise_sys.controller;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.user.UserNotificationResponse;
import com.chilleric.franchise_sys.dto.user.UserRequest;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.pusher.PusherResponse;
import com.chilleric.franchise_sys.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController extends AbstractController<UserService> {

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "add-new-user")
  public ResponseEntity<CommonResponse<String>> addNewUser(
    @RequestBody UserRequest userRequest,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    checkAddCondition(result.getEditable(), UserRequest.class);
    service.createNewUser(userRequest, result.getLoginId(), result.isServer());
    return new ResponseEntity<CommonResponse<String>>(
      new CommonResponse<String>(
        true,
        null,
        LanguageMessageKey.CREATE_USER_SUCCESS,
        HttpStatus.OK.value(),
        result.getViewPoints().get(UserResponse.class.getSimpleName()),
        result.getEditable().get(UserRequest.class.getSimpleName())
      ),
      null,
      HttpStatus.OK.value()
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-detail-user")
  public ResponseEntity<CommonResponse<UserResponse>> getUserDetail(
    @RequestParam(required = true) String id,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    checkAccessability(result.getLoginId(), id, false, result.isServer());
    if (id.compareTo(result.getLoginId()) == 0) {
      return response(
        service.findOneUserById(id),
        LanguageMessageKey.SUCCESS,
        result.getViewPoints().get(UserResponse.class.getSimpleName()),
        result.getEditable().get(UserRequest.class.getSimpleName())
      );
    }
    return response(
      Optional.of(
        filterResponse(service.findOneUserById(id).get(), result.getViewPoints())
      ),
      LanguageMessageKey.SUCCESS,
      result.getViewPoints().get(UserResponse.class.getSimpleName()),
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-notifications-user")
  public ResponseEntity<CommonResponse<List<UserNotificationResponse>>> getNotifications(
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    return response(
      service.getNotifications(result.getLoginId()),
      LanguageMessageKey.SUCCESS,
      result.getViewPoints().get(UserResponse.class.getSimpleName()),
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-notification-config")
  public ResponseEntity<CommonResponse<PusherResponse>> getNotificationConfig(
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    return response(
      service.getYourPusher(result.getLoginId()),
      LanguageMessageKey.SUCCESS,
      result.getViewPoints().get(UserResponse.class.getSimpleName()),
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-list-users")
  public ResponseEntity<CommonResponse<ListWrapperResponse<UserResponse>>> getListUsers(
    @RequestParam(required = false, defaultValue = "1") int page,
    @RequestParam(required = false, defaultValue = "10") int pageSize,
    @RequestParam Map<String, String> allParams,
    @RequestParam(defaultValue = "asc") String keySort,
    @RequestParam(defaultValue = "modified") String sortField,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    return response(
      service.getAllUsers(
        allParams,
        keySort,
        page,
        pageSize,
        "",
        result.getLoginId(),
        result.isServer()
      ),
      LanguageMessageKey.SUCCESS,
      result.getViewPoints().get(UserResponse.class.getSimpleName()),
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-list-your-users")
  public ResponseEntity<CommonResponse<ListWrapperResponse<UserResponse>>> getListYourUsers(
    @RequestParam(required = false, defaultValue = "1") int page,
    @RequestParam(required = false, defaultValue = "10") int pageSize,
    @RequestParam Map<String, String> allParams,
    @RequestParam(defaultValue = "asc") String keySort,
    @RequestParam(defaultValue = "modified") String sortField,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    return response(
      service.getYourUsers(allParams, keySort, page, pageSize, "", result.getLoginId()),
      LanguageMessageKey.SUCCESS,
      result.getViewPoints().get(UserResponse.class.getSimpleName()),
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "update-user")
  public ResponseEntity<CommonResponse<String>> updateUser(
    @RequestBody UserRequest userRequest,
    @RequestParam(required = true) String id,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    checkAccessability(result.getLoginId(), id, true, result.isServer());
    preventItSelf(result.getLoginId(), id);
    service.updateUserById(
      id,
      userRequest,
      result.getEditable().get(UserRequest.class.getSimpleName())
    );
    return new ResponseEntity<CommonResponse<String>>(
      new CommonResponse<String>(
        true,
        null,
        LanguageMessageKey.UPDATE_USER_SUCCESS,
        HttpStatus.OK.value(),
        result.getViewPoints().get(UserResponse.class.getSimpleName()),
        result.getEditable().get(UserRequest.class.getSimpleName())
      ),
      null,
      HttpStatus.OK.value()
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "change-status-user")
  public ResponseEntity<CommonResponse<String>> changeStatusUser(
    @RequestParam String id,
    HttpServletRequest request
  ) {
    ValidationResult result = validateToken(request);
    checkAccessability(result.getLoginId(), id, true, result.isServer());
    preventItSelf(result.getLoginId(), id);
    service.changeStatusUser(id);
    return new ResponseEntity<CommonResponse<String>>(
      new CommonResponse<String>(
        true,
        null,
        LanguageMessageKey.CHANGE_STATUS_USER_SUCCESS,
        HttpStatus.OK.value(),
        result.getViewPoints().get(UserResponse.class.getSimpleName()),
        result.getEditable().get(UserRequest.class.getSimpleName())
      ),
      null,
      HttpStatus.OK.value()
    );
  }
}
