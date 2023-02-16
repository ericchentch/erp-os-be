package com.chilleric.franchise_sys.repository.crm_repository.inventoryConfirmation;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventory_confirmations")
public class InventoryConfirmation {
  private ObjectId _id;

  private ObjectId inventoryId;
  private ObjectId reviewerId;
  private int realQuantity;
  private Date confirmDate;
}
