package com.chilleric.franchise_sys.repository.crm_repository.hotel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class HotelRepositoryImpl extends AbstractRepo implements HotelRepository {

  @Override
  public void insertAndUpdate(Hotel hotel) {
    crmDBTemplate.save(hotel, "hotels");
  }

  @Override
  public long getTotalPage(Map<String, String> allParams) {
    Query query = generateQueryMongoDB(allParams, Hotel.class, "", "", 0, 0);
    crmDBTemplate.count(query, Hotel.class);
    return 0;
  }

  @Override
  public Optional<List<Hotel>> getHotels(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField) {
    Query query = generateQueryMongoDB(allParams, Hotel.class, keySort, sortField, page, pageSize);

    return crmFind(query, Hotel.class);
  }

  @Override
  public void delete(String hotelId) {
    Query query =
        generateQueryMongoDB(Map.ofEntries(Map.entry("_id", hotelId)), Hotel.class, "", "", 0, 0);
    crmDBTemplate.remove(query, Hotel.class);
  }

}
