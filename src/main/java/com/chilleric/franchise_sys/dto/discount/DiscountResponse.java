package com.chilleric.franchise_sys.dto.discount;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {
    private String id;
    private String name;
    private float maximum;
    private float minimumApply;
    private List<String> userIds;
}
