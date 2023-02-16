package com.chilleric.franchise_sys.dto.common;

import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
  private String loginId;
  private Map<String, List<ViewPoint>> viewPoints;
  private Map<String, List<ViewPoint>> editable;
  private boolean isServer;
}
