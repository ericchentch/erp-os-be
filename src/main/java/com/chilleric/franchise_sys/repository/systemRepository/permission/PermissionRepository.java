package com.chilleric.franchise_sys.repository.systemRepository.permission;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

public interface PermissionRepository {

  Optional<List<Permission>> getPermissions(Map<String, String> allParams, String keySort, int page,
      int pageSize, String sortField);

  Optional<Permission> getPermissionById(String id);


  Optional<List<Permission>> getPermissionByUserId(String userId);

  void insertAndUpdate(Permission permission);

  void deletePermission(String id);

  Map<String, List<ViewPoint>> getViewPointSelect();

  Map<String, List<ViewPoint>> getEditableSelect();

  long getTotal(Map<String, String> allParams);
}
