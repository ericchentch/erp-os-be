package com.chilleric.franchise_sys.repository.common_entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendNotification {
  private boolean updatePath;
  private boolean updateSidebar;
}
