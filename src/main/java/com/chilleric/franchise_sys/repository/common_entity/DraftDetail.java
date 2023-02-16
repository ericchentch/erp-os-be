package com.chilleric.franchise_sys.repository.common_entity;

import com.chilleric.franchise_sys.repository.common_enum.TypeObjectBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftDetail {
  private TypeObjectBill type;
  private ObjectId id;
  private int quantity;
}
