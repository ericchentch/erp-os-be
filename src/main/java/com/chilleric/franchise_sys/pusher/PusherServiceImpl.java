package com.chilleric.franchise_sys.pusher;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.repository.system_repository.user.User;
import com.chilleric.franchise_sys.repository.system_repository.user.UserNotification;
import com.chilleric.franchise_sys.repository.system_repository.user.UserRepository;
import com.chilleric.franchise_sys.utils.DateFormat;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.rest.Pusher;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

  @Autowired
  protected UserRepository userRepository;

  public void sendNotification(String title, String body, List<String> interests) {
    PushNotifications beamsClient = new PushNotifications(PUSHER_INSTANCE, PUSHER_SECRET);

    Map<String, Map> publishRequest = new HashMap<>();
    Map<String, String> webNotification = new HashMap<>();
    webNotification.put("title", title);
    webNotification.put("body", body);
    webNotification.put("icon", WEB_LINK + "favicon.ico");
    webNotification.put("deep_link", WEB_LINK);
    Map<String, Map> web = new HashMap<>();
    web.put("notification", webNotification);
    publishRequest.put("web", web);
    try {
      interests.forEach(
        thisId -> {
          User user = userRepository
            .getEntityByAttribute(thisId, "notificationId")
            .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
          List<UserNotification> listNoti = user.getNotifications();
          if (listNoti.size() == 20) {
            listNoti.remove(19);
            listNoti.add(0, new UserNotification(body, DateFormat.getCurrentTime()));
          } else {
            listNoti.add(0, new UserNotification(body, DateFormat.getCurrentTime()));
          }
          user.setNotifications(listNoti);
          userRepository.insertAndUpdate(user);
        }
      );
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
