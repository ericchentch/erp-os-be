package com.chilleric.franchise_sys.pusher;

import java.util.List;

public interface PusherService {
  void sendNotification(String title, String body, List<String> interests);
}
