package com.chilleric.franchise_sys.pusher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.rest.Pusher;

@Component
public class PusherServiceImpl implements PusherService {

  @Value("${pusher.instance}")
  protected String PUSHER_INSTANCE;

  @Value("${pusher.secret}")
  protected String PUSHER_SECRET;

  @Value("${web.link}")
  protected String WEB_LINK;

  @Value("${pusher.app.key}")
  protected String PUSHER_APP_KEY;

  @Value("${pusher.app.id}")
  protected String PUSHER_APP_ID;

  @Value("${pusher.app.secret}")
  protected String PUSHER_APP_SECRET;

  @Value("${pusher.cluster}")
  protected String PUSHER_CLUSTER;

  public void sendNotification(String title, String body, List<String> interests) {
    PushNotifications beamsClient = new PushNotifications(PUSHER_INSTANCE, PUSHER_SECRET);

    Map<String, Map> publishRequest = new HashMap<>();
    Map<String, String> webNotification = new HashMap<>();
    webNotification.put("title", title);
    webNotification.put("body", body);
    webNotification.put("icon", "https://franchise-sys-frontend.netlify.app/favicon.ico");
    webNotification.put("deep_link", WEB_LINK);
    Map<String, Map> web = new HashMap<>();
    web.put("notification", webNotification);
    publishRequest.put("web", web);
    try {
      beamsClient.publishToInterests(interests, publishRequest);
    } catch (IOException | InterruptedException | URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void pushInfo(String channel, String event, Object data) {
    Pusher pusher = new Pusher(PUSHER_APP_ID, PUSHER_APP_KEY, PUSHER_APP_SECRET);
    pusher.setCluster(PUSHER_CLUSTER);
    pusher.setEncrypted(true);

    pusher.trigger(channel, event, data);

  }
}