package com.chilleric.franchise_sys.repository.crm_repository.variant;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
