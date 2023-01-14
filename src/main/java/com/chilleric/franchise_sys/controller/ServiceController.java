package com.chilleric.franchise_sys.controller;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.service.ServiceRequest;
import com.chilleric.franchise_sys.dto.service.ServiceResponse;
import com.chilleric.franchise_sys.service.services.ServicesService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(name = "service-controller")
public class ServiceController extends AbstractController<ServicesService> {
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("create-service")
    public ResponseEntity<CommonResponse<String>> createService(@RequestBody ServiceRequest serviceRequest,
                                                                HttpServletRequest request) {
        validateToken(request);
        service.createService(serviceRequest);
        return new ResponseEntity<>(new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()), null, HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("get-service-list")
    public ResponseEntity<CommonResponse<ListWrapperResponse<ServiceResponse>>> getServices(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam Map<String, String> allParams,
        @RequestParam(defaultValue = "asc") String keySort,
        @RequestParam(defaultValue = "modified") String sortField, HttpServletRequest request) {
        validateToken(request);
        return response(service.getServices(allParams, keySort, page, pageSize, sortField),
            LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("get-service-by-id")
    public ResponseEntity<CommonResponse<ServiceResponse>> getServiceById(@RequestParam String serviceId,
                                                                          HttpServletRequest request) {
        validateToken(request);
        return response(service.getServiceById(serviceId), LanguageMessageKey.SUCCESS, new ArrayList<>(),
            new ArrayList<>());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "update-service")
    public ResponseEntity<CommonResponse<String>> updateService(@RequestParam String serviceId,
        @RequestBody ServiceRequest serviceRequest, HttpServletRequest request) {
        validateToken(request);

        service.updateService(serviceRequest, serviceId);
        return new ResponseEntity<CommonResponse<String>>(new CommonResponse<>(true, null,
            LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
            null, HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "delete-service-by-id")
    public ResponseEntity<CommonResponse<String>> deleteService(@RequestParam String serviceId,
                                                              HttpServletRequest request) {
        validateToken(request);

        service.deleteService(serviceId);

        return new ResponseEntity<CommonResponse<String>>(
            new CommonResponse<String>(true, null, LanguageMessageKey.SHIFT_DELETE_SUCCESS,
                HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
            null, HttpStatus.OK.value());
    }
}
