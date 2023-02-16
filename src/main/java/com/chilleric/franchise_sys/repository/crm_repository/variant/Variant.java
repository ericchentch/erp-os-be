package com.chilleric.franchise_sys.repository.crm_repository.variant;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "variants")
public class Variant {
  private ObjectId _id;

  private String name;
  private float price;
  private List<ObjectId> roomTypeId;
}
