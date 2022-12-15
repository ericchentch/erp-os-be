package com.chilleric.franchise_sys.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserResponse {
    private String name;
    private String id;
}
