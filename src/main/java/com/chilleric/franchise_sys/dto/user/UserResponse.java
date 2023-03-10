package com.chilleric.franchise_sys.dto.user;

import com.chilleric.franchise_sys.repository.system_repository.user.User.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
  private String id;
  private String avatar;
  private TypeAccount type;
  private String username;
  private int gender;
  private String dob;
  private String address;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String created;
  private String modified;
  private boolean verified;
  private boolean verify2FA;
  private int active;
}
