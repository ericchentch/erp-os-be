package com.chilleric.franchise_sys.repository.informationRepository.inventory;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventories")
public class Inventory {
  private ObjectId _id;

  private ObjectId belongToId;
  private String name;
  private float price;
  private int quantity;
}
