package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "table")
public class TableController extends AbstractController {

  // protected static final Map<String, List<String>> IgnoreView = Map.ofEntries(
  // entry("PermissionResponse", Arrays.asList("id")), entry("UserResponse", Arrays.asList("id")),
  // entry("PathResponse", Arrays.asList("id", "userIds")));

  // protected static final Map<String, List<String>> IgnoreEdit =
  // Map.ofEntries(entry("PermissionRequest", Arrays.asList("id")),
  // entry("UserRequest", Arrays.asList("id")), entry("PathRequest", Arrays.asList("id")));


  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-user")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldUser(
      HttpServletRequest request) {
    validateToken(request);
    Optional<List<ViewPoint>> result = Optional.of(Arrays.asList("id", "password").stream()
        .map(thisField -> new ViewPoint(thisField, thisField)).collect(Collectors.toList()));
    return response(result, LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-permission")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldPermission(
      HttpServletRequest request) {
    validateToken(request);
    Optional<List<ViewPoint>> result = Optional.of(Arrays.asList("id", "userIds").stream()
        .map(thisField -> new ViewPoint(thisField, thisField)).collect(Collectors.toList()));
    return response(result, LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-path")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldPath(
      HttpServletRequest request) {
    validateToken(request);
    Optional<List<ViewPoint>> result = Optional.of(Arrays.asList("id", "icon").stream()
        .map(thisField -> new ViewPoint(thisField, thisField)).collect(Collectors.toList()));
    return response(result, LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }


}
