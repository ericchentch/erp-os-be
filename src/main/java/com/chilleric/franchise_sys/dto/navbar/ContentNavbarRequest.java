package com.chilleric.franchise_sys.dto.navbar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentNavbarRequest {
  private String mainItem;
  private List<String> childrenItem;
}
