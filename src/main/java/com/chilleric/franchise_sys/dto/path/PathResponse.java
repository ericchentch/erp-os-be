package com.chilleric.franchise_sys.dto.path;

import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathResponse {
    private String id;
    private String label;
    private String path;
    private TypeAccount type;
    private String icon;
}
