package com.chilleric.franchise_sys.repository.informationRepository.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.language.LanguageResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ServiceRepositoryImpl extends AbstractRepo implements ServiceRepository {

    @Override
    public Optional<List<Service>> getServices(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Service.class, keySort, sortField, page, pageSize);

        return informationFind(query, Service.class);
    }

    @Override
    public void insertAndUpdate(Service service) {
        informationDBTemplate.save(service, "services");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Service.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Service.class);
    }

    @Override
    public void deleteService(String serviceId) {
        try {
            ObjectId _id = new ObjectId(serviceId);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            informationDBTemplate.remove(query, Service.class);
        }catch (IllegalArgumentException e) {
            APP_LOGGER.error("wrong type_id");
            throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
    }

}
