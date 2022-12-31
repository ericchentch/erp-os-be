package com.chilleric.franchise_sys.service.bill;

import com.chilleric.franchise_sys.dto.bill.BillRequest;

public interface BillService {
    void addItemIntoDraftBill(BillRequest billRequest);
}
