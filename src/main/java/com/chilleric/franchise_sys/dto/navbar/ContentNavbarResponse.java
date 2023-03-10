package com.chilleric.franchise_sys.dto.navbar;

import com.chilleric.franchise_sys.dto.path.PathResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentNavbarResponse {
  private PathResponse mainItem;
  private List<PathResponse> childrenItem;
}
