package com.chilleric.franchise_sys.repository.informationRepository.shift;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ShiftRepository {
    Optional<List<Shift>> getShifts(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(Shift shift);

    long getTotalPage(Map<String, String> allParams);
}