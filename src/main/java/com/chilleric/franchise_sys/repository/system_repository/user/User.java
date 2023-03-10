package com.chilleric.franchise_sys.repository.system_repository.user;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  public enum TypeAccount {
    INTERNAL,
    EXTERNAL
  }

  private ObjectId _id;
  private TypeAccount type;
  private String username;
  private String password;
  private int gender;
  private String dob;
  private String address;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private List<String> tokens;
  private Date created;
  private Date modified;
  private boolean verified;
  private boolean verify2FA;
  private int deleted;
  private String avatar;
  private ObjectId notificationId;
  private String channelId;
  private ObjectId eventId;
  private List<UserNotification> notifications;
}
