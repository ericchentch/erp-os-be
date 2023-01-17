package com.chilleric.franchise_sys.service.hotel;

import java.util.Optional;
import com.chilleric.franchise_sys.dto.hotel.HotelRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelResponse;

public interface HotelService {

  void createNewHotel(HotelRequest hotelRequest);

  void updateHotel(String hotelId, HotelRequest hotelRequest);

  Optional<HotelResponse> getHotelById(String hotelId);

  void deleteHotel(String hotelId);
}

