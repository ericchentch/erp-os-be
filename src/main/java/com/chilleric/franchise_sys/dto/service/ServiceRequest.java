package com.chilleric.franchise_sys.dto.service;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequest {
    @NotNull(message = LanguageMessageKey.SERVICE_NAME_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SERVICE_NAME_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SERVICE_NAME_REQUIRED)
    private String serviceName;

    @NotNull(message = LanguageMessageKey.SERVICE_PRICE_REQUIRED)
    @NotEmpty(message = LanguageMessageKey.SERVICE_PRICE_REQUIRED)
    @NotBlank(message = LanguageMessageKey.SERVICE_PRICE_REQUIRED)
    private float price;
}
