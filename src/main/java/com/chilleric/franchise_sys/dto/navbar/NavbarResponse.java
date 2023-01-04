package com.chilleric.franchise_sys.dto.navbar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavbarResponse {
    private String id;
    private String name;
    private List<String> userIds;
    private List<String> content;
}
