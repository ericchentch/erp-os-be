package com.chilleric.franchise_sys.service.bill;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.bill.DraftDetailResponse;
import com.chilleric.franchise_sys.dto.bill.TimelineResponse;
import com.chilleric.franchise_sys.dto.roomType.RoomTypeResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.common_entity.DraftDetail;
import com.chilleric.franchise_sys.repository.common_entity.Timeline;
import com.chilleric.franchise_sys.repository.common_enum.TypeObjectBill;
import com.chilleric.franchise_sys.repository.crmRepository.bill.BillRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.service.roomType.RoomTypeService;

@Service
public class BillResponseServiceImpl extends AbstractService<BillRepository>
        implements BillResponseService {

    protected final RoomTypeService roomTypeService;

    public BillResponseServiceImpl(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @Override
    public List<DraftDetailResponse> getListDraftDetailResponse(List<DraftDetail> draftDetailList) {
        return draftDetailList.stream().map(draftDetail -> {
            Object objectResponse = new Object();
            if (draftDetail.getType() == TypeObjectBill.ROOMTYPE) {
                objectResponse = roomTypeService.getRoomTypeById(draftDetail.getId().toString())
                        .orElse(null);
            }
            // # TODO: need to complete this when having case: Variants and Services.
            return new DraftDetailResponse(draftDetail.getType(), objectResponse,
                    draftDetail.getQuantity());
        }).filter(e -> e.getObjectResponse() != null).collect(Collectors.toList());
    }

    @Override
    public List<TimelineResponse> getListTimelineResponse(List<Timeline> timelineList) {
        return timelineList.stream()
                .map(timeline -> new TimelineResponse(timeline.getPaymentMethod(),
                        timeline.getAmountOfPayment(), timeline.getCreated().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public float getBillTotal(List<DraftDetail> draftDetailList) {
        // TODO: need add discount workflow
        return draftDetailList.stream().map(element -> {
            float price = 0;
            if (element.getType() == TypeObjectBill.ROOMTYPE) {
                RoomTypeResponse roomTypeResponse =
                        roomTypeService.getRoomTypeById(element.getId().toString())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        LanguageMessageKey.ROOM_TYPE_NOT_FOUND));
                price = roomTypeResponse.getStockPrice();
            }
            return price * element.getQuantity();
        }).reduce((float) 0, Float::sum);
    }

    @Override
    public float getPaidTotal(List<Timeline> timelineList) {
        return timelineList.stream().map(Timeline::getAmountOfPayment).reduce((float) 0,
                Float::sum);
    }

}
