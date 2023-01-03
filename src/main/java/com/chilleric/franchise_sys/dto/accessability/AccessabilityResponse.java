package com.chilleric.franchise_sys.dto.accessability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessabilityResponse {
    private String id;
    private String accessAccountName;
    private String accessUsername;
    private boolean editable;
}
