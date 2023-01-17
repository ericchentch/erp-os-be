package com.chilleric.franchise_sys.repository.informationRepository.serve;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServeRepository {
    Optional<List<Serve>> getServes(Map<String, String> allParams, String keySort, int page,
                                    int pageSize, String sortField);

    void insertAndUpdate(Serve serve);

    long getTotalPage(Map<String, String> allParams);

    void deleteServe(String serviceId);
}
