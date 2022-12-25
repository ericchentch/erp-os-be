package com.chilleric.franchise_sys.repository.informationRepository.roomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class RoomTypeRepositoryImpl extends AbstractRepo implements RoomTypeRepository {

    @Override
    public Optional<List<RoomType>> getRoomTypes(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, RoomType.class, keySort, sortField, page, pageSize);
        return informationFind(query, RoomType.class);

    }

    @Override
    public void insertAndUpdate(RoomType roomType) {
        informationDBTemplate.save(roomType);
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, RoomType.class, "", "", 0, 0);
        return informationDBTemplate.count(query, RoomType.class);
    }

}
