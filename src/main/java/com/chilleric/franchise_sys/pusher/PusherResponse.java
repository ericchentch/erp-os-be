package com.chilleric.franchise_sys.pusher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PusherResponse {
  private String notificationId;
  private String channelId;
  private String eventId;
}
