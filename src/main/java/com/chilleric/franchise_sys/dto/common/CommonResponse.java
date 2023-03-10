package com.chilleric.franchise_sys.dto.common;

import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
  private boolean success;
  private T result;
  private String message;
  private int statusCode;
  private List<ViewPoint> viewPoints;
  private List<ViewPoint> editable;
}
