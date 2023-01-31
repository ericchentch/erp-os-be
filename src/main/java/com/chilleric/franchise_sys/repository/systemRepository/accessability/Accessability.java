package com.chilleric.franchise_sys.repository.systemRepository.accessability;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "accessability")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accessability {
  private ObjectId _id;
  private ObjectId userId;
  private ObjectId targetId;
  private boolean editable;
  private boolean isServer;
}
