package com.chilleric.franchise_sys.dto.bill;

import com.chilleric.franchise_sys.repository.common_enum.TypeObjectBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftDetailRequest {
  private TypeObjectBill type;
  private String id;
  private int quantity;
}
