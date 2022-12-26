package com.chilleric.franchise_sys.repository.informationRepository.varian;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class VarianRepositoryImpl extends AbstractRepo implements VarianRepository {

    @Override
    public Optional<List<Varian>> getVarians(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Varian.class, keySort, sortField, page, pageSize);
        return informationFind(query, Varian.class);
    }

    @Override
    public void insertAndUpdate(Varian varian) {
        informationDBTemplate.save(varian, "varians");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Varian.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Varian.class);
    }

}
