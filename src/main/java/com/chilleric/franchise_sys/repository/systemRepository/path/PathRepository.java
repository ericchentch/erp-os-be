package com.chilleric.franchise_sys.repository.systemRepository.path;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PathRepository {
    Optional<List<Path>> getPaths(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(Path path);

    void deletePath(String id);

    long getTotal(Map<String, String> allParams);
}
