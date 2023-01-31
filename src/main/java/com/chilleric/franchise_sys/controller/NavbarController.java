package com.chilleric.franchise_sys.controller;

import java.util.Map;
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
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;
import com.chilleric.franchise_sys.service.navbar.NavbarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "navbar")
public class NavbarController extends AbstractController<NavbarService> {
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-path-list")
  public ResponseEntity<CommonResponse<ListWrapperResponse<NavbarResponse>>> getPaths(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam Map<String, String> allParams,
      @RequestParam(defaultValue = "asc") String keySort,
      @RequestParam(defaultValue = "modified") String sortField, HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(
        service.getListNavbar(allParams, keySort, page, pageSize, sortField, result.getLoginId()),
        LanguageMessageKey.SUCCESS,
        result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
        result.getEditable().get(NavbarRequest.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-navbar-detail-id")
  public ResponseEntity<CommonResponse<NavbarResponse>> getPathsDetailId(@RequestParam String id,
      HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.getNavbarDetailById(id, result.getLoginId()),
        LanguageMessageKey.SUCCESS,
        result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
        result.getEditable().get(NavbarRequest.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-navbar-detail-name")
  public ResponseEntity<CommonResponse<NavbarResponse>> getPathsDetailName(
      @RequestParam String name, HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.getNavbarDetailByName(name, result.getLoginId()),
        LanguageMessageKey.SUCCESS,
        result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
        result.getEditable().get(NavbarRequest.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "add-new-navbar")
  public ResponseEntity<CommonResponse<String>> addNewNavbar(
      @RequestBody NavbarRequest navbarRequest, HttpServletRequest httpServletRequest) {
    ValidationResult result = validateToken(httpServletRequest);
    checkAddCondition(result.getEditable(), NavbarRequest.class);
    service.addNewNavbar(navbarRequest, result.getLoginId(), result.isServer());
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.NAVBAR_ADDED,
            HttpStatus.OK.value(), result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
            result.getEditable().get(NavbarRequest.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "update-navbar")
  public ResponseEntity<CommonResponse<String>> updateNavbar(
      @RequestBody NavbarRequest navbarRequest, HttpServletRequest httpServletRequest,
      @RequestParam String id) {
    ValidationResult result = validateToken(httpServletRequest);
    checkAddCondition(result.getEditable(), NavbarRequest.class);
    service.updateNavbar(navbarRequest, result.getLoginId(), id);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.NAVBAR_UPDATED,
            HttpStatus.OK.value(), result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
            result.getEditable().get(NavbarRequest.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "delete-path")
  public ResponseEntity<CommonResponse<String>> deleteNavbar(@RequestParam String id,
      HttpServletRequest httpServletRequest) {
    ValidationResult result = validateToken(httpServletRequest);
    checkAccessability(result.getLoginId(), id, true, result.isServer());
    service.deleteNavbar(id);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.NAVBAR_DELETED,
            HttpStatus.OK.value(), result.getViewPoints().get(NavbarResponse.class.getSimpleName()),
            result.getEditable().get(NavbarRequest.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }
}
