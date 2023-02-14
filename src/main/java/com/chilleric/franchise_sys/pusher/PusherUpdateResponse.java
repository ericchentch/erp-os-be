package com.chilleric.franchise_sys.pusher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PusherUpdateResponse {
  private boolean isUpdateAccessPath;
  private boolean isUpdateNotification;
}
