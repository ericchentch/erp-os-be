package com.chilleric.franchise_sys.repository.system_repository.path;

import com.chilleric.franchise_sys.repository.system_repository.user.User.TypeAccount;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paths")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {
  private ObjectId _id;
  private String label;
  private String path;
  private TypeAccount type;
  private List<ObjectId> userId;
  private String icon;
}
