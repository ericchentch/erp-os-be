package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityRequest;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityResponse;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.service.accessability.AccessabilityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(name = "accesses")
public class AccessabilityController extends AbstractController<AccessabilityService> {
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "share-target")
    public ResponseEntity<CommonResponse<String>> shareTarget(
            @RequestBody AccessabilityRequest accessabilityRequest, HttpServletRequest request,
            @RequestParam String targetId) {
        ValidationResult result = validateToken(request);
        checkAccessability(result.getLoginId(), targetId, true);
        service.shareAccess(accessabilityRequest, result.getLoginId(), targetId);
        return new ResponseEntity<>(new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS,
                HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()), null,
                HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "get-access")
    public ResponseEntity<CommonResponse<List<AccessabilityResponse>>> getAccessByTarget(
            HttpServletRequest request, @RequestParam String targetId) {
        ValidationResult result = validateToken(request);
        checkAccessability(result.getLoginId(), targetId, true);;
        return response(service.getAccessByTargetId(targetId, result.getLoginId()),
                LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
    }
}
