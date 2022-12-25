package com.chilleric.franchise_sys.repository.informationRepository.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class ConfigRepositoryImpl extends AbstractRepo implements ConfigRepository {

    @Override
    public Optional<List<Config>> getConfigs(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Config.class, keySort, sortField, page, pageSize);

        return informationFind(query, Config.class);
    }

    @Override
    public void insertAndUpdate(Config config) {
        informationDBTemplate.save(config, "configs");
    }

    @Override
    public long getTotalPage(Map<String, String> allParams) {
        Query query = generateQueryMongoDB(allParams, Config.class, "", "", 0, 0);
        return informationDBTemplate.count(query, Config.class);
    }

}
