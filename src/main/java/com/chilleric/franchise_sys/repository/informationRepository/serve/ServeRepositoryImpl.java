package com.chilleric.franchise_sys.repository.informationRepository.serve;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ServeRepositoryImpl extends AbstractRepo implements ServeRepository {

    @Override
    public Optional<List<Serve>> getServes(Map<String, String> allParams, String keySort,
                                           int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Serve.class, keySort, sortField, page, pageSize);

        return informationFind(query, Serve.class);
    }

    @Override
    public void insertAndUpdate(Serve serve) {
        informationDBTemplate.save(serve, "services");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Serve.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Serve.class);
    }

    @Override
    public void deleteServe(String serviceId) {
        try {
            ObjectId _id = new ObjectId(serviceId);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            informationDBTemplate.remove(query, Serve.class);
        }catch (IllegalArgumentException e) {
            APP_LOGGER.error("wrong type_id");
            throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
        }
    }

}
