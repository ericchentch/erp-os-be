package com.chilleric.franchise_sys.service.bill;

import java.util.List;
import com.chilleric.franchise_sys.dto.bill.DraftDetailResponse;
import com.chilleric.franchise_sys.dto.bill.TimelineResponse;
import com.chilleric.franchise_sys.repository.common_entity.DraftDetail;
import com.chilleric.franchise_sys.repository.common_entity.Timeline;

public interface BillResponseService {
  List<DraftDetailResponse> getListDraftDetailResponse(List<DraftDetail> draftDetailList);

  List<TimelineResponse> getListTimelineResponse(List<Timeline> timelineList);

  float getBillTotal(List<DraftDetail> draftDetailResponseList);

  float getPaidTotal(List<Timeline> timelineList);

}
