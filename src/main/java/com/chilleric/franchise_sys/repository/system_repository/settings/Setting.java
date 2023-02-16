package com.chilleric.franchise_sys.repository.system_repository.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
  private ObjectId _id;
  private ObjectId userId;
  private String languageKey;
}
