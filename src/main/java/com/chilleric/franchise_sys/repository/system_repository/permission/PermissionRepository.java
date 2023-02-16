package com.chilleric.franchise_sys.repository.system_repository.permission;

import static java.util.Map.entry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.chilleric.franchise_sys.dto.hotel.HotelRequest;
import com.chilleric.franchise_sys.dto.hotel.HotelResponse;
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;
import com.chilleric.franchise_sys.dto.path.PathRequest;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.dto.permission.PermissionRequest;
import com.chilleric.franchise_sys.dto.permission.PermissionResponse;
import com.chilleric.franchise_sys.dto.user.UserRequest;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.abstract_repository.SystemRepository;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

@Repository
public class PermissionRepository extends SystemRepository<Permission> {

  public Map<String, List<ViewPoint>> getEditableSelect() {
    List<Class<?>> viewPointList = List.of(UserRequest.class, PermissionRequest.class,
        PathRequest.class, HotelRequest.class, NavbarRequest.class);
    Map<String, List<ViewPoint>> result = new HashMap<>();
    viewPointList.forEach(clazz -> {
      List<ViewPoint> attributes = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
        attributes.add(new ViewPoint(field.getName(), field.getName()));
      }
      result.put(clazz.getSimpleName(), attributes);
    });
    return removeId(result);
  }

  public Map<String, List<ViewPoint>> getViewPointSelect() {
    List<Class<?>> viewPointList = List.of(UserResponse.class, PermissionResponse.class,
        PathResponse.class, HotelResponse.class, NavbarResponse.class);
    Map<String, List<ViewPoint>> result = new HashMap<>();
    viewPointList.forEach(clazz -> {
      List<ViewPoint> attributes = new ArrayList<>();
      for (Field field : clazz.getDeclaredFields()) {
        attributes.add(new ViewPoint(field.getName(), field.getName()));
      }
      result.put(clazz.getSimpleName(), attributes);
    });
    return removeId(result);
  }

  private Map<String, List<ViewPoint>> removeId(Map<String, List<ViewPoint>> thisView) {
    return thisView.entrySet().stream().map((key) -> {
      List<ViewPoint> newValue = key.getValue().stream()
          .filter(viewList -> viewList.getKey().compareTo("id") != 0).collect(Collectors.toList());
      return entry(key.getKey(), newValue);
    }).collect(
        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
  }

}
