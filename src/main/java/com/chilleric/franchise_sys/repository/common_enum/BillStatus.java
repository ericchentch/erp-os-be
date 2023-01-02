package com.chilleric.franchise_sys.repository.common_enum;

public enum BillStatus {
    UNPAID("Unpaid"), PENDING("Pending"), COMPLETED("Completed"), CANCELED("Canceled"), FAIL(
            "Fail"), REFUND("Refund");

    private String billStatus;

    BillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillStatus() {
        return billStatus;
    }
}
