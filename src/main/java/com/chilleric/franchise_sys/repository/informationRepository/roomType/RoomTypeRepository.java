package com.chilleric.franchise_sys.repository.informationRepository.roomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomTypeRepository {
    Optional<List<RoomType>> getRoomTypes(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(RoomType roomType);

    long getTotalPage(Map<String, String> allParams);
}
