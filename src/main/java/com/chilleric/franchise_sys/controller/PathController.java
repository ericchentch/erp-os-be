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
import com.chilleric.franchise_sys.dto.path.PathRequest;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.service.path.PathService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "paths")
public class PathController extends AbstractController<PathService> {
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-path-list")
  public ResponseEntity<CommonResponse<ListWrapperResponse<PathResponse>>> getPaths(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam Map<String, String> allParams,
      @RequestParam(defaultValue = "asc") String keySort,
      @RequestParam(defaultValue = "modified") String sortField, HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(
        service.getPaths(allParams, keySort, page, pageSize, sortField, result.getLoginId()),
        LanguageMessageKey.SUCCESS, result.getViewPoints().get(PathResponse.class.getSimpleName()),
        result.getEditable().get(PathRequest.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-path-detail")
  public ResponseEntity<CommonResponse<PathResponse>> getPathsDetail(@RequestParam String id,
      HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    return response(service.getPathDetail(id), LanguageMessageKey.SUCCESS,
        result.getViewPoints().get(PathResponse.class.getSimpleName()),
        result.getEditable().get(PathRequest.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "add-new-path")
  public ResponseEntity<CommonResponse<String>> addNewPath(@RequestBody PathRequest pathRequest,
      HttpServletRequest httpServletRequest) {
    ValidationResult result = validateToken(httpServletRequest);
    service.addNewPath(pathRequest, result.getLoginId());
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.PATH_ADD_SUCCESS,
            HttpStatus.OK.value(), result.getViewPoints().get(PathResponse.class.getSimpleName()),
            result.getEditable().get(PathRequest.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "delete-path")
  public ResponseEntity<CommonResponse<String>> deletePath(@RequestParam String id,
      HttpServletRequest httpServletRequest) {
    ValidationResult result = validateToken(httpServletRequest);
    checkAccessability(result.getLoginId(), id, true);
    service.deletePath(id);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.DELETE_PATH_SUCCESS,
            HttpStatus.OK.value(), result.getViewPoints().get(PathResponse.class.getSimpleName()),
            result.getEditable().get(PathRequest.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }
}
