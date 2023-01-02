package com.chilleric.franchise_sys.repository.common_enum;

public enum BillStatus {
    UNPAID("Unpaid"), COMPLETED("Completed"), CANCELED("Canceled"), REFUND("Refund");

    private String billStatus;

    BillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillStatus() {
        return billStatus;
    }
}
