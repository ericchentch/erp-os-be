package com.chilleric.franchise_sys.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftDetailRequest {
    private Class<?> clazz;
    private String id;
    private int quantity;
}
