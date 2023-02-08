package com.chilleric.franchise_sys.service.bill;

import java.util.List;
import java.util.Optional;
import com.chilleric.franchise_sys.dto.bill.BillRequest;
import com.chilleric.franchise_sys.dto.bill.BillResponse;
import com.chilleric.franchise_sys.repository.common_entity.DraftDetail;
import com.chilleric.franchise_sys.repository.common_entity.Timeline;
import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import com.chilleric.franchise_sys.repository.information_repository.bill.Bill;

public interface BillService {
  void createBill(BillRequest billRequest);

  void updateBill(BillRequest billRequest, String billId);

  Optional<BillResponse> getBillResponseById(String billId);

  Optional<Bill> findBillById(String billId);

  BillStatus getBillStatus(List<Timeline> timelineList, List<DraftDetail> draftDetailList);

}
