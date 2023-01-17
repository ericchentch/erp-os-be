package com.chilleric.franchise_sys.repository.common_entity;

import java.util.Date;
import com.chilleric.franchise_sys.repository.common_enum.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
  private PaymentMethod paymentMethod;
  private float amountOfPayment;
  private Date created;
}
