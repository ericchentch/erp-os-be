package com.chilleric.franchise_sys.repository.crm_repository.hotel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HotelRepository {
  Optional<List<Hotel>> getHotels(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);

  void insertAndUpdate(Hotel hotel);

  long getTotalPage(Map<String, String> allParams);

  void delete(String hotelId);
}
