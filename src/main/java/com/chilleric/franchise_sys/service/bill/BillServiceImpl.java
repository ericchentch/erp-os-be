package com.chilleric.franchise_sys.service.bill;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.bill.BillRequest;
import com.chilleric.franchise_sys.dto.bill.BillResponse;
import com.chilleric.franchise_sys.dto.discount.DiscountResponse;
import com.chilleric.franchise_sys.dto.user.UserResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.common_entity.DraftDetail;
import com.chilleric.franchise_sys.repository.common_entity.Timeline;
import com.chilleric.franchise_sys.repository.common_enum.BillStatus;
import com.chilleric.franchise_sys.repository.crmRepository.bill.Bill;
import com.chilleric.franchise_sys.repository.crmRepository.bill.BillRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.service.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl extends AbstractService<BillRepository> implements BillService {

    protected final UserService userService;

    protected final BillResponseService billResponseService;

    public BillServiceImpl(UserService userService, BillResponseService billResponseService) {
        this.userService = userService;
        this.billResponseService = billResponseService;
    }

    @Override
    public void createBill(BillRequest billRequest) {
        validate(billRequest);
        validateStringIsObjectId(billRequest.getCustomerId());

        Bill bill = new Bill();

        bill.set_id(new ObjectId());
        bill.setCustomerId(new ObjectId(billRequest.getCustomerId()));
        bill.setPaidCustomer(new UserResponse());

        bill.setDraftDetails(billRequest.getDraftDetail().stream().map(draftDetailRequest -> {
            validateStringIsObjectId(draftDetailRequest.getId());

            return new DraftDetail(draftDetailRequest.getType(),
                new ObjectId(draftDetailRequest.getId()), draftDetailRequest.getQuantity());
        }).collect(Collectors.toList()));

        bill.setConfirmedDetail(new ArrayList<>());
        bill.setDiscount(new DiscountResponse());
        bill.setDeposit(0);
        bill.setTotal(0);

        bill.setTimeline(billRequest.getTimeline().stream().map(
                timelineRequest -> new Timeline(timelineRequest.getPaymentMethod(),
                    timelineRequest.getAmountPayment(), timelineRequest.getCreated()))
            .collect(Collectors.toList()));

        bill.setStatus(getBillStatus(bill.getTimeline(), bill.getDraftDetails()));

        repository.insertAndUpdate(bill);
    }

    @Override
    public void updateBill(BillRequest billRequest, String billId) {
        validate(billRequest);

        Bill bill = findBillById(billId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.BILL_NOT_FOUND));

        if (bill.getStatus() == BillStatus.COMPLETED || bill.getStatus() == BillStatus.REFUND) {
            throw new ResourceNotFoundException(LanguageMessageKey.BILL_CAN_NOT_BE_UPDATE);
        }

        bill.setDraftDetails(billRequest.getDraftDetail().stream().map(draftDetailRequest -> {
            validateStringIsObjectId(draftDetailRequest.getId());

            return new DraftDetail(draftDetailRequest.getType(),
                new ObjectId(draftDetailRequest.getId()), draftDetailRequest.getQuantity());
        }).collect(Collectors.toList()));

        bill.setTimeline(billRequest.getTimeline().stream().map(
                timelineRequest -> new Timeline(timelineRequest.getPaymentMethod(),
                    timelineRequest.getAmountPayment(), timelineRequest.getCreated()))
            .collect(Collectors.toList()));

        // # TODO: handle status of bill.
        bill.setStatus(getBillStatus(bill.getTimeline(), bill.getDraftDetails()));

        repository.insertAndUpdate(bill);
    }



    @Override
    public Optional<BillResponse> getBillResponseById(String billId) {
        Bill bill = findBillById(billId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.BILL_NOT_FOUND));
        BillResponse billResponse = new BillResponse();

        billResponse.setId(billId);
        billResponse.setCustomerResponse(
            userService.findOneUserById(bill.getCustomerId().toString()).orElseThrow(
                () -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USER)));
        billResponse.setPaidCustomer(new UserResponse());

        billResponse.setDraftDetails(
            billResponseService.getListDraftDetailResponse(bill.getDraftDetails()));

        billResponse.setTimeline(billResponseService.getListTimelineResponse(bill.getTimeline()));
        billResponse.setStatus(bill.getStatus());

        billResponse.setTotal(billResponseService.getBillTotal(bill.getDraftDetails()));
        billResponse.setPaidTotal(billResponseService.getPaidTotal(bill.getTimeline()));

        // # TODO: need to complete when having data.
        billResponse.setConfirmedDetail(new ArrayList<>());
        billResponse.setDiscount(new DiscountResponse());
        billResponse.setDeposit(100);

        return Optional.of(billResponse);
    }

    @Override
    public Optional<Bill> findBillById(String billId) {
        validateStringIsObjectId(billId);

        List<Bill> bills =
            repository.getBills(Map.ofEntries(Map.entry("_id", billId)), "", 0, 0, "").orElse(null);
        if (bills != null) {
            return Optional.of(bills.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public BillStatus getBillStatus(List<Timeline> timelineList,
        List<DraftDetail> draftDetailList) {
        if (timelineList.size() == 0) {
            return BillStatus.UNPAID;
        }

        float paidTotal = billResponseService.getPaidTotal(timelineList);
        float billTotal = billResponseService.getBillTotal(draftDetailList);
        if (paidTotal == billTotal) {
            return BillStatus.COMPLETED;
        }

        if (timelineList.get(timelineList.size() - 1).getAmountOfPayment() < 0) {
            return BillStatus.REFUND;
        }

        // Default status:
        return BillStatus.CANCELED;
    }

}
