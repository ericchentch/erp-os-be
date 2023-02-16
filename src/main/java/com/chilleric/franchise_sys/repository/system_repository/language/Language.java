package com.chilleric.franchise_sys.repository.system_repository.language;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "languages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {
  private ObjectId _id;
  private String language;
  private String key;
  private Map<String, String> dictionary;
}
