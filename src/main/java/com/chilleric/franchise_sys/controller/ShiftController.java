package com.chilleric.franchise_sys.controller;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.bill.BillRequest;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.language.LanguageResponse;
import com.chilleric.franchise_sys.dto.shift.ShiftRequest;
import com.chilleric.franchise_sys.dto.shift.ShiftResponse;
import com.chilleric.franchise_sys.service.shift.ShiftService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(name = "shift-controller")
public class ShiftController extends AbstractController<ShiftService> {
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("create-shift")
  public ResponseEntity<CommonResponse<String>> createShift(@RequestBody ShiftRequest shiftRequest,
      HttpServletRequest request) {
    validateToken(request);

    service.createShift(shiftRequest);
    return new ResponseEntity<>(
      new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS, HttpStatus.OK.value(),
              new ArrayList<>(), new ArrayList<>()), null, HttpStatus.OK.value()
    );
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("get-shift-list")
  public ResponseEntity<CommonResponse<ListWrapperResponse<ShiftResponse>>> getShifts(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam Map<String, String> allParams,
      @RequestParam(defaultValue = "asc") String keySort,
      @RequestParam(defaultValue = "modified") String sortField,
      HttpServletRequest request) {
      ValidationResult result = validateToken(request);
      return response(service.getShifts(allParams, keySort, page, pageSize, sortField),
          LanguageMessageKey.SUCCESS,
          result.getViewPoints().get(LanguageResponse.class.getSimpleName()),
          result.getEditable().get(LanguageResponse.class.getSimpleName()));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("get-shift-by-id")
    public ResponseEntity<CommonResponse<ShiftResponse>> getShiftById(@RequestParam String shiftId,
        HttpServletRequest request) {
      validateToken(request);
      return response(service.getShiftById(shiftId), LanguageMessageKey.SUCCESS,
          new ArrayList<>(), new ArrayList<>());
  }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("get-shift-by-name")
    public ResponseEntity<CommonResponse<ShiftResponse>> searchShiftByName(@RequestParam String shiftName,
                                                                      HttpServletRequest request) {
        validateToken(request);
        return response(service.searchShiftByName(shiftName), LanguageMessageKey.SUCCESS,
            new ArrayList<>(), new ArrayList<>());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "update-shift")
    public ResponseEntity<CommonResponse<String>> updateShift(@RequestParam String shiftId,
                                                             @RequestBody ShiftRequest shiftRequest, HttpServletRequest request) {
        validateToken(request);

        service.updateShift(shiftRequest, shiftId);
        return new ResponseEntity<CommonResponse<String>>(
            new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS, HttpStatus.OK.value(),
                new ArrayList<>(), new ArrayList<>()), null, HttpStatus.OK.value());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "delete-shift-by-id")
    public ResponseEntity<CommonResponse<String>> deleteShift(@RequestParam String shiftId,
                                                              HttpServletRequest httpServletRequest) {
        validateToken(httpServletRequest);

        service.deleteShift(shiftId);

        return new ResponseEntity<CommonResponse<String>>(
            new CommonResponse<String>(true, null, LanguageMessageKey.HOTEL_DELETE_SUCCESS,
                HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
            null, HttpStatus.OK.value());
    }
}
