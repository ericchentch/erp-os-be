package com.chilleric.franchise_sys.repository.informationRepository.roomType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;
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

    @Override
    public Optional<List<RoomType>> getRoomTypesByHotel(String hotelId) {
        try {
            ObjectId hotel_id = new ObjectId(hotelId);
            Query query = new Query();
            query.addCriteria(Criteria.where("hotelId").is(hotel_id));
            return informationFind(query, RoomType.class);
        } catch (IllegalArgumentException e) {
            APP_LOGGER.error("hotel id is wrong type");
            return Optional.empty();
        }
    }

    @Override
    public void delete(String roomTypeId) {
        try {
            ObjectId roomType_id = new ObjectId(roomTypeId);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(roomType_id));
            informationDBTemplate.remove(query, RoomType.class);
        } catch (IllegalArgumentException e) {
            APP_LOGGER.error("Room type id is wrong type");
            throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
    }

}
