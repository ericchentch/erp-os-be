package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.dto.hotel.HotelRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelResponse;
import com.chilleric.franchise_sys.service.hotel.HotelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "hotel")
public class HotelController extends AbstractController<HotelService> {
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-hotel-by-id")
  public ResponseEntity<CommonResponse<HotelResponse>> getHotel(
      @RequestParam(required = true) String hotelId, HttpServletRequest request) {
    validateToken(request);

    return response(service.getHotelById(hotelId), LanguageMessageKey.SUCCESS, new ArrayList<>(),
        new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "create-hotel")
  public ResponseEntity<CommonResponse<String>> createHotel(
      @RequestBody(required = true) HotelRequest hotelRequest, HttpServletRequest request) {
    ValidationResult result = validateToken(request);
    service.createNewHotel(hotelRequest);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.HOTEL_ADD_SUCCESS,
            HttpStatus.OK.value(), result.getViewPoints().get(HotelResponse.class.getSimpleName()),
            result.getEditable().get(HotelResponse.class.getSimpleName())),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "update-hotel")
  public ResponseEntity<CommonResponse<String>> updateHotel(@RequestParam String hotelId,
      @RequestBody HotelRequest hotelRequest, HttpServletRequest httpServletRequest) {
    validateToken(httpServletRequest);
    service.updateHotel(hotelId, hotelRequest);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.HOTEL_UPDATE_SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @DeleteMapping(value = "delete-hotel-by-id")
  public ResponseEntity<CommonResponse<String>> deleteHotel(@RequestParam String hotelId,
      HttpServletRequest httpServletRequest) {
    validateToken(httpServletRequest);

    service.deleteHotel(hotelId);

    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.HOTEL_DELETE_SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

}
