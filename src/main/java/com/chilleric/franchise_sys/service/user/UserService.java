package com.chilleric.franchise_sys.service.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.user.UserRequest;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

public interface UserService {

  void createNewUser(UserRequest userRequest, String loginId);

  void updateUserById(String userId, UserRequest userRequest, List<ViewPoint> viewPoints);

  Optional<UserResponse> findOneUserById(String userId);

  Optional<ListWrapperResponse<UserResponse>> getYourUsers(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId);

  Optional<ListWrapperResponse<UserResponse>> getAllUsers(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId);

  void changeStatusUser(String userId);
}
