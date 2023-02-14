package com.chilleric.franchise_sys.repository.systemRepository.user;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotification {
  private String content;
  private Date sendTime;
}
