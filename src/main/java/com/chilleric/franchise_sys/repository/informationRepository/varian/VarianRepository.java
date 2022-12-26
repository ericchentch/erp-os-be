package com.chilleric.franchise_sys.repository.informationRepository.varian;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VarianRepository {
    Optional<List<Varian>> getVarians(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(Varian varian);

    long getTotalPage(Map<String, String> allParams);
}
