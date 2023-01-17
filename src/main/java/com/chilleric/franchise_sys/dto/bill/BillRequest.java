package com.chilleric.franchise_sys.dto.bill;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
  @NotBlank(message = LanguageMessageKey.BILL_CUSTOMER_ID_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.BILL_CUSTOMER_ID_REQUIRED)
  @NotNull(message = LanguageMessageKey.BILL_CUSTOMER_ID_REQUIRED)
  private String customerId;

  @NotNull(message = LanguageMessageKey.BILL_DRAFT_DETAIL_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.BILL_DRAFT_DETAIL_REQUIRED)
  private List<DraftDetailRequest> draftDetail;

  @NotNull(message = LanguageMessageKey.BILL_TIMELINE_REQUIRED)
  private List<TimelineRequest> timeline;

  @NotNull(message = LanguageMessageKey.BILL_STATUS_REQUIRED)
  private BillStatus status;
}
