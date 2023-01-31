package com.chilleric.franchise_sys.service.path;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.path.PathRequest;
import com.chilleric.franchise_sys.dto.path.PathResponse;

public interface PathService {
  Optional<ListWrapperResponse<PathResponse>> getPaths(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId);

  Optional<PathResponse> getPathDetail(String id);

  Optional<List<String>> getPathAccess(String loginId);

  void addNewPath(PathRequest pathRequest, String loginId, boolean isServer);

  void deletePath(String id);

}
