package com.chilleric.franchise_sys.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsResponse {
    private boolean darkTheme;
    private String languageKey;
}
