package com.chilleric.franchise_sys.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;

public class ObjectUtilities {

  public static Map<String, List<ViewPoint>> mergePermission(Map<String, List<ViewPoint>> source,
      Map<String, List<ViewPoint>> target) {
    Map<String, List<ViewPoint>> result = new HashMap<>();
    result.putAll(source);
    result.putAll(target);
    return result;
  }
}
