package com.chilleric.franchise_sys.dto.serve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServeResponse {
    private String id;

    private String serviceName;

    private float price;
}
