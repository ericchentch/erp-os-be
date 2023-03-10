package com.chilleric.franchise_sys.dto.path;

import com.chilleric.franchise_sys.repository.system_repository.user.User.TypeAccount;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathResponse {
  private String id;
  private String label;
  private String path;
  private TypeAccount type;
  private List<String> userId;
  private String icon;
}
