package com.chilleric.franchise_sys.service.hotel;

import com.chilleric.franchise_sys.dto.hotel.HotelRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelResponse;
import java.util.Optional;

public interface HotelService {
  void createNewHotel(HotelRequest hotelRequest);

  void updateHotel(String hotelId, HotelRequest hotelRequest);

  Optional<HotelResponse> getHotelById(String hotelId);

  void deleteHotel(String hotelId);
}
