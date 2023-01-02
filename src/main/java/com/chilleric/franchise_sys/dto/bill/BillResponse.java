package com.chilleric.franchise_sys.dto.bill;

import com.chilleric.franchise_sys.dto.discount.DiscountResponse;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private String id;

    private UserResponse customerResponse;

    private UserResponse paidCustomer;
    private List<DraftDetailResponse> draftDetails;
    private List<Object> confirmedDetail;
    private DiscountResponse discount;
    private float deposit;
    private float total;

    private List<TimelineResponse> timeline;

    private float paidTotal;
    private BillStatus status;
}
