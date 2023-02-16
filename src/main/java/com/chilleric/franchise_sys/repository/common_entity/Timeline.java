package com.chilleric.franchise_sys.repository.common_entity;

import com.chilleric.franchise_sys.repository.common_enum.PaymentMethod;
import java.util.Date;
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
