package com.chilleric.franchise_sys.repository.common_enum;

public enum TypeObjectBill {
    SERVICES("Service"), VARIANTS("Variant"), ROOMTYPE("RoomType");

    private String typeObjectBill;

    TypeObjectBill(String typeObjectBill) {
        this.typeObjectBill = typeObjectBill;
    }

    public String getTypeObjectBill() {
        return typeObjectBill;
    }
}
