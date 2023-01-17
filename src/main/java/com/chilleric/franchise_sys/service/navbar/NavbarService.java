package com.chilleric.franchise_sys.service.navbar;

import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;

public interface NavbarService {
    Optional<ListWrapperResponse<NavbarResponse>> getListNavbar(Map<String, String> allParams,
            String keySort, int page, int pageSize, String sortField, String loginId);

    Optional<NavbarResponse> getNavbarDetailById(String id, String loginId);

    Optional<NavbarResponse> getNavbarDetailByName(String name, String loginId);

    void addNewNavbar(NavbarRequest navbarRequest, String loginId);

    void updateNavbar(NavbarRequest navbarRequest, String loginId, String id);

    void deleteNavbar(String id);
}
