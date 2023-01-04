package com.chilleric.franchise_sys.repository.systemRepository.navbar;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NavbarRepository {
    Optional<List<Navbar>> getNavbarList(Map<String, String> allParams, String keySort, int page,
            int pageSize, String sortField);

    void insertAndUpdate(Navbar navbar);

    void deletePath(String id);

    long getTotal(Map<String, String> allParams);
}
