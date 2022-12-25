package com.chilleric.franchise_sys.repository.informationRepository.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConfigRepository {
    Optional<List<Config>> getConfigs(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);


    void insertAndUpdate(Config config);

    long getTotalPage(Map<String, String> allParams);
}
