package com.chilleric.franchise_sys.controller;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.serve.ServeRequest;
import com.chilleric.franchise_sys.dto.serve.ServeResponse;
import com.chilleric.franchise_sys.service.serve.ServeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(name = "serve-controller")
public class ServeController extends AbstractController<ServeService> {
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("create-serve")
    public ResponseEntity<CommonResponse<String>> createServe(@RequestBody ServeRequest serveRequest,
                                                                HttpServletRequest request) {
        validateToken(request);
        service.createServe(serveRequest);
        return new ResponseEntity<>(new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()), null, HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("get-serve-list")
    public ResponseEntity<CommonResponse<ListWrapperResponse<ServeResponse>>> getServes(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam Map<String, String> allParams,
        @RequestParam(defaultValue = "asc") String keySort,
        @RequestParam(defaultValue = "modified") String sortField, HttpServletRequest request) {
        validateToken(request);
        return response(service.getServes(allParams, keySort, page, pageSize, sortField),
            LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("get-serve-by-id")
    public ResponseEntity<CommonResponse<ServeResponse>> getServeById(@RequestParam String serveId,
                                                                        HttpServletRequest request) {
        validateToken(request);
        return response(service.getServeById(serveId), LanguageMessageKey.SUCCESS, new ArrayList<>(),
            new ArrayList<>());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "update-serve")
    public ResponseEntity<CommonResponse<String>> updateServe(@RequestParam String serveId,
                                                                @RequestBody ServeRequest serveRequest, HttpServletRequest request) {
        validateToken(request);

        service.updateServe(serveRequest, serveId);
        return new ResponseEntity<CommonResponse<String>>(new CommonResponse<>(true, null,
            LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
            null, HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "delete-serve-by-id")
    public ResponseEntity<CommonResponse<String>> deleteServe(@RequestParam String serveId,
       ServeRequest serveRequest ,HttpServletRequest request) {
        validateToken(request);

        service.updateServe(serveRequest ,serveId);

        return new ResponseEntity<CommonResponse<String>>(
            new CommonResponse<String>(true, null, LanguageMessageKey.SHIFT_DELETE_SUCCESS,
                HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
            null, HttpStatus.OK.value());
    }
}
