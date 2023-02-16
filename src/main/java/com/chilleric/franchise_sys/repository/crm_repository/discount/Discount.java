package com.chilleric.franchise_sys.repository.crm_repository.discount;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "discounts")
public class Discount {
  private ObjectId _id;

  private String name;
  private float discount;
  private float maximum;
  private float minimumApply;
  private List<ObjectId> targetIds;
}
