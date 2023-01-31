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
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeRequest;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeResponse;
import com.chilleric.franchise_sys.service.roomType.RoomTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "room-type")
public class RoomTypeController extends AbstractController<RoomTypeService> {

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "create-room-type")
  public ResponseEntity<CommonResponse<String>> createRoomType(
      @RequestBody RoomTypeRequest roomTypeRequest, HttpServletRequest request) {
    validateToken(request);

    service.createNewRoomType(roomTypeRequest);
    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.ROOM_TYPE_ADD_SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-room-type-by-hotel-id")
  public ResponseEntity<CommonResponse<ListWrapperResponse<RoomTypeResponse>>> getListRoomTypeByHotelId(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = true) String hotelId,
      @RequestParam(defaultValue = "asc") String keySort,
      @RequestParam(defaultValue = "modified") String sortField, HttpServletRequest request) {
    validateToken(request);
    return response(service.getRoomTypeByHotelId(hotelId, keySort, page, pageSize, sortField),
        LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-detailed-room-type")
  public ResponseEntity<CommonResponse<RoomTypeResponse>> getDetailedRoomType(
      @RequestParam(required = true) String roomTypeId, HttpServletRequest request) {
    validateToken(request);
    return response(service.getRoomTypeById(roomTypeId), LanguageMessageKey.SUCCESS,
        new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "update-room-type")
  public ResponseEntity<CommonResponse<String>> updateRoomType(
      @RequestParam(required = true) String roomTypeId,
      @RequestBody RoomTypeRequest roomTypeRequest, HttpServletRequest request) {
    validateToken(request);

    service.updateRoomType(roomTypeId, roomTypeRequest);
    return new ResponseEntity<CommonResponse<String>>(new CommonResponse<String>(true, null,
        LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")

  @DeleteMapping(value = "delete-room-type-by-id")
  public ResponseEntity<CommonResponse<String>> deleteRoomType(
      @RequestParam(required = true) String roomTypeId, HttpServletRequest request) {
    validateToken(request);
    service.deleteRoomType(roomTypeId);
    return new ResponseEntity<CommonResponse<String>>(new CommonResponse<String>(true, null,
        LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

}
