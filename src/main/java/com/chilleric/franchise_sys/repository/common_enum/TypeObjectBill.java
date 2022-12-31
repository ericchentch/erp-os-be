package com.chilleric.franchise_sys.repository.common_enum;

public enum TypeObjectBill {
    SERVICES("services"), VARIANTS("variants"), ROOMTYPE("roomType");

    private String typeObjectBill;

    TypeObjectBill(String typeObjectBill) {
        this.typeObjectBill = typeObjectBill;
    }

    public String getTypeObjectBill() {
        return typeObjectBill;
    }
}
