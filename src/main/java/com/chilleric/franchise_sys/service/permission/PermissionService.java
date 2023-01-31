package com.chilleric.franchise_sys.service.permission;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.permission.PermissionRequest;
import com.chilleric.franchise_sys.dto.permission.PermissionResponse;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

public interface PermissionService {

  Optional<ListWrapperResponse<PermissionResponse>> getYourPermissions(
      Map<String, String> allParams, String keySort, int page, int pageSize, String sortField,
      String loginId);

  Optional<ListWrapperResponse<PermissionResponse>> getAllPermissions(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId, boolean isServer);

  Optional<PermissionResponse> getPermissionById(String id, String loginId);

  void addNewPermissions(PermissionRequest permissionRequest, String loginId, boolean isServer);

  void editPermission(PermissionRequest permissionRequest, String id, List<ViewPoint> viewPoints,
      String loginId, boolean isServer);

  void deletePermission(String id);

  Map<String, List<ViewPoint>> getViewPointSelect(String loginId);

  List<ViewPoint> getPermissionView();

  Map<String, List<ViewPoint>> getEditableSelect(String loginId);

}
