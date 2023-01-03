package com.chilleric.franchise_sys.dto.hotel;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {
	@NotBlank(message = LanguageMessageKey.HOTEL_NAME_REQUIRED)
	@NotEmpty(message = LanguageMessageKey.HOTEL_NAME_REQUIRED)
	@NotNull(message = LanguageMessageKey.HOTEL_NAME_REQUIRED)
	private String name;

	@NotBlank(message = LanguageMessageKey.HOTEL_DESCRIPTION_REQUIRED)
	@NotEmpty(message = LanguageMessageKey.HOTEL_DESCRIPTION_REQUIRED)
	@NotNull(message = LanguageMessageKey.HOTEL_DESCRIPTION_REQUIRED)
	private String description;

	@NotNull(message = LanguageMessageKey.HOTEL_LINK_IMAGE_REQUIRED)
	@NotEmpty(message = LanguageMessageKey.HOTEL_LINK_IMAGE_REQUIRED)
	private List<@NotBlank(message = LanguageMessageKey.HOTEL_LINK_IMAGE_REQUIRED) @NotEmpty(
			message = LanguageMessageKey.HOTEL_LINK_IMAGE_REQUIRED) String> linkImages;

	@NotNull(message = LanguageMessageKey.HOTEL_CLIENT_REQUIRED)
	@NotEmpty(message = LanguageMessageKey.HOTEL_CLIENT_REQUIRED)
	private List<ClientRequest> client;

	@NotNull(message = LanguageMessageKey.HOTEL_VAT_REQUIRED)
	@Min(0)
	@Max(100)
	private float VAT;

	@NotNull(message = LanguageMessageKey.HOTEL_MAX_REFUND_REQUIRED)
	@Min(0)
	@Max(100)
	private float maxRefund;

	@NotNull(message = LanguageMessageKey.HOTEL_VAT_REQUIRED)
	@Min(0)
	private int maxDaysRefund;
}
