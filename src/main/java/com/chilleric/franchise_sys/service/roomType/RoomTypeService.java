package com.chilleric.franchise_sys.service.roomType;

import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeRequest;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeResponse;
import com.chilleric.franchise_sys.repository.crm_repository.roomType.RoomType;

public interface RoomTypeService {
  void createNewRoomType(RoomTypeRequest roomTypeRequest);

  void updateRoomType(String roomTypeId, RoomTypeRequest roomTypeRequest);

  Optional<RoomTypeResponse> getRoomTypeById(String roomTypeId);

  Optional<ListWrapperResponse<RoomTypeResponse>> getRoomTypeByHotelId(String hotelId,
      String keySort, int page, int pageSize, String sortField);

  void deleteRoomType(String roomTypeId);

  RoomType validateExistRoomType(String roomTypeId);
}
