package com.chilleric.franchise_sys.dto.bill;

import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    private String customerId;
    private List<DraftDetailRequest> draftDetail;
    private List<TimelineRequest> timeline;

    private BillStatus status;
}
