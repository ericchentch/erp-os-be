package com.chilleric.franchise_sys.controller;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.dto.permission.PermissionResponse;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "table")
public class TableController extends AbstractController {

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-user")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldUser(
    HttpServletRequest request
  ) {
    ValidationResult resultAuthentication = validateToken(request);
    return response(
      getResult(
        removeAttributes(resultAuthentication.getViewPoints(), IgnoreView),
        UserResponse.class
      ),
      LanguageMessageKey.SUCCESS,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-permission")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldPermission(
    HttpServletRequest request
  ) {
    ValidationResult resultAuthentication = validateToken(request);
    return response(
      getResult(
        removeAttributes(resultAuthentication.getViewPoints(), IgnoreView),
        PermissionResponse.class
      ),
      LanguageMessageKey.SUCCESS,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-ignore-field-path")
  public ResponseEntity<CommonResponse<List<ViewPoint>>> getIgnoreFieldPath(
    HttpServletRequest request
  ) {
    ValidationResult resultAuthentication = validateToken(request);
    return response(
      getResult(
        removeAttributes(resultAuthentication.getViewPoints(), IgnoreView),
        PathResponse.class
      ),
      LanguageMessageKey.SUCCESS,
      new ArrayList<>(),
      new ArrayList<>()
    );
  }

  Optional<List<ViewPoint>> getResult(
    Map<String, List<ViewPoint>> viewPoint,
    Class<?> getClass
  ) {
    if (viewPoint.containsKey(getClass.getSimpleName())) {
      return Optional.of(viewPoint.get(getClass.getSimpleName()));
    } else {
      return Optional.of(new ArrayList<>());
    }
  }
}
