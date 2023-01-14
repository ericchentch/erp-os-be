package com.chilleric.franchise_sys.repository.informationRepository.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceRepository {
    Optional<List<Service>> getServices(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(Service service);

    long getTotalPage(Map<String, String> allParams);

    void deleteService(String serviceId);
}
