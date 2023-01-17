package com.chilleric.franchise_sys.service.hotel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.hotel.ClientRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.informationRepository.hotel.Hotel;
import com.chilleric.franchise_sys.repository.informationRepository.hotel.HotelRepository;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class HotelServiceImpl extends AbstractService<HotelRepository> implements HotelService {

  @Override
  public void createNewHotel(HotelRequest hotelRequest) {
    validate(hotelRequest);
    Hotel hotel = objectMapper.convertValue(hotelRequest, Hotel.class);
    ObjectId hotelId = new ObjectId();
    hotel.set_id(hotelId);
    repository.insertAndUpdate(hotel);
  }

  @Override
  public void updateHotel(String hotelId, HotelRequest hotelRequest) {
    validate(hotelRequest);
    List<Hotel> hotels =
        repository.getHotels(Map.ofEntries(Map.entry("_id", hotelId)), "", 0, 0, "").get();
    if (hotels.size() == 0) {
      throw new ResourceNotFoundException(LanguageMessageKey.HOTEL_NOT_FOUND);
    }
    Hotel hotel = hotels.get(0);

    Hotel newHotel = objectMapper.convertValue(hotelRequest, Hotel.class);
    newHotel.set_id(hotel.get_id());

    repository.insertAndUpdate(newHotel);
  }

  @Override
  public Optional<HotelResponse> getHotelById(String hotelId) {
    List<Hotel> hotels =
        repository.getHotels(Map.ofEntries(Map.entry("_id", hotelId)), "", 0, 0, "").get();
    if (hotels.size() == 0) {
      throw new ResourceNotFoundException(LanguageMessageKey.HOTEL_NOT_FOUND);
    }
    Hotel hotel = hotels.get(0);

    return Optional.of(new HotelResponse(hotel.get_id().toString(), hotel.getName(),
        hotel.getDescription(), hotel.getLinkImages(),
        hotel.getClient().stream()
            .map((clientInformation) -> new ClientRequest(clientInformation.getAddress(),
                clientInformation.getPhone()))
            .collect(Collectors.toList()),
        hotel.getBillDeposit(), hotel.getVAT(), hotel.getMaxRefund(), hotel.getMaxDaysRefund()));
  }

  @Override
  public void deleteHotel(String hotelId) {
    List<Hotel> hotels =
        repository.getHotels(Map.ofEntries(Map.entry("_id", hotelId)), "", 0, 0, "").get();
    if (hotels.size() == 0) {
      throw new ResourceNotFoundException(LanguageMessageKey.HOTEL_NOT_FOUND);
    }
    // TODO: need check other constraint repository. Can delete if have nothing use this hotelId
    repository.delete(hotelId);
  }

}
