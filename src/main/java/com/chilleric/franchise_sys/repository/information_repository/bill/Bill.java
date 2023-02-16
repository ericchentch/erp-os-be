package com.chilleric.franchise_sys.repository.information_repository.bill;

import com.chilleric.franchise_sys.dto.bill.DraftDetailResponse;
import com.chilleric.franchise_sys.dto.discount.DiscountResponse;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.common_entity.DraftDetail;
import com.chilleric.franchise_sys.repository.common_entity.Timeline;
import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bills")
public class Bill {
  private ObjectId _id;

  private ObjectId customerId;
  private UserResponse paidCustomer;

  private List<DraftDetail> draftDetails;
  private List<DraftDetailResponse> confirmedDetail;

  private DiscountResponse discount;
  private float deposit;
  private float total;

  private List<Timeline> timeline;

  private BillStatus status;
}
