package com.chilleric.franchise_sys.service.navbar;

import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;
import java.util.Map;
import java.util.Optional;

public interface NavbarService {
  Optional<ListWrapperResponse<NavbarResponse>> getListNavbar(
    Map<String, String> allParams,
    String keySort,
    int page,
    int pageSize,
    String sortField,
    String loginId
  );

  Optional<NavbarResponse> getNavbarDetailById(String id, String loginId);

  Optional<NavbarResponse> getNavbarDetailByName(String name, String loginId);

  void addNewNavbar(NavbarRequest navbarRequest, String loginId, boolean isServer);

  void updateNavbar(NavbarRequest navbarRequest, String loginId, String id);

  void deleteNavbar(String id);
}
