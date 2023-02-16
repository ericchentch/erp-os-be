package com.chilleric.franchise_sys.service.roomType;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeRequest;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.crm_repository.roomType.RoomType;
import com.chilleric.franchise_sys.repository.crm_repository.roomType.RoomTypeRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeServiceImpl
  extends AbstractService<RoomTypeRepository>
  implements RoomTypeService {

  @Override
  public void createNewRoomType(RoomTypeRequest roomTypeRequest) {
    validate(roomTypeRequest);
    if (!ObjectId.isValid(roomTypeRequest.getHotelId())) {
      throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
    }

    RoomType roomType = new RoomType(
      new ObjectId(),
      new ObjectId(roomTypeRequest.getHotelId()),
      roomTypeRequest.getName(),
      roomTypeRequest.getLinkImages(),
      roomTypeRequest.getRooms(),
      roomTypeRequest.getRate(),
      roomTypeRequest.getStockPrice()
    );
    repository.insertAndUpdate(roomType);
  }

  @Override
  public void updateRoomType(String roomTypeId, RoomTypeRequest roomTypeRequest) {
    validate(roomTypeRequest);
    validateStringIsObjectId(roomTypeRequest.getHotelId());

    RoomType roomType = validateExistRoomType(roomTypeId);

    RoomType newRoomType = new RoomType(
      roomType.get_id(),
      new ObjectId(roomTypeRequest.getHotelId()),
      roomTypeRequest.getName(),
      roomTypeRequest.getLinkImages(),
      roomTypeRequest.getRooms(),
      roomTypeRequest.getRate(),
      roomTypeRequest.getStockPrice()
    );
    repository.insertAndUpdate(newRoomType);
  }

  @Override
  public Optional<RoomTypeResponse> getRoomTypeById(String roomTypeId) {
    RoomType roomType = validateExistRoomType(roomTypeId);

    return Optional.of(
      new RoomTypeResponse(
        roomType.get_id().toString(),
        roomType.getHotelId().toString(),
        roomType.getName(),
        roomType.getLinkImages(),
        roomType.getRooms(),
        roomType.getRate(),
        roomType.getStockPrice()
      )
    );
  }

  @Override
  public void deleteRoomType(String roomTypeId) {
    validateExistRoomType(roomTypeId);
    repository.deleteById(roomTypeId);
  }

  @Override
  public RoomType validateExistRoomType(String roomTypeId) {
    List<RoomType> roomTypes = repository
      .getListOrEntity(Map.ofEntries(Map.entry("_id", roomTypeId)), "", 0, 0, "")
      .get();
    if (roomTypes.size() == 0) {
      throw new ResourceNotFoundException(LanguageMessageKey.ROOM_TYPE_NOT_FOUND);
    }
    return roomTypes.get(0);
  }

  @Override
  public Optional<ListWrapperResponse<RoomTypeResponse>> getRoomTypeByHotelId(
    String hotelId,
    String keySort,
    int page,
    int pageSize,
    String sortField
  ) {
    Map<String, String> params = new HashMap<>();
    params.put("hotelId", hotelId);
    List<RoomType> roomTypes = repository
      .getListOrEntity(params, keySort, page, pageSize, sortField)
      .get();

    return Optional.of(
      new ListWrapperResponse<RoomTypeResponse>(
        roomTypes
          .stream()
          .map(
            roomType ->
              new RoomTypeResponse(
                roomType.get_id().toString(),
                roomType.getHotelId().toString(),
                roomType.getName(),
                roomType.getLinkImages(),
                roomType.getRooms(),
                roomType.getRate(),
                roomType.getStockPrice()
              )
          )
          .collect(Collectors.toList()),
        page,
        pageSize,
        repository.getTotalPage(params)
      )
    );
  }
}
