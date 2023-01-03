package com.chilleric.franchise_sys.repository.systemRepository.settings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.repository.AbstractRepo;

@Repository
public class SettingRepositoryImpl extends AbstractRepo implements SettingRepository {

    @Override
    public Optional<List<Setting>> getSettings(Map<String, String> allParams, String keySort,
            int page, int pageSize, String sortField) {
        Query query =
                generateQueryMongoDB(allParams, Setting.class, keySort, sortField, page, pageSize);
        return systemFind(query, Setting.class);
    }

    @Override
    public void insertAndUpdate(Setting setting) {
        systemDBTemplate.save(setting, "settings");
    }

}
