package com.chilleric.franchise_sys.dto.bill;

import com.chilleric.franchise_sys.repository.common_enum.PaymentMethod;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimelineRequest {
  private PaymentMethod paymentMethod;
  private float amountPayment;
  private Date created;
}
