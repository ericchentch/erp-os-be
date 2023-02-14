package com.chilleric.franchise_sys.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationResponse {
  private String content;
  private String sendTime;
}
