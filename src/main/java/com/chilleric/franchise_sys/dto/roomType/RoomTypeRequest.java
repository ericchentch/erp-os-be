package com.chilleric.franchise_sys.dto.roomType;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRequest {
  @NotBlank(message = LanguageMessageKey.ROOM_TYPE_HOTELID_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.ROOM_TYPE_HOTELID_REQUIRED)
  @NotNull(message = LanguageMessageKey.ROOM_TYPE_HOTELID_REQUIRED)
  private String hotelId;

  @NotBlank(message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED)
  @NotNull(message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED)
  private String name;

  @NotNull(message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED)
  private List<@NotBlank(message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED) @NotEmpty(
    message = LanguageMessageKey.ROOM_TYPE_NAME_REQUIRED
  ) String> linkImages;

  @NotNull(message = LanguageMessageKey.ROOM_TYPE_ROOMS_REQUIRED)
  @NotEmpty(message = LanguageMessageKey.ROOM_TYPE_ROOMS_REQUIRED)
  private List<@NotEmpty(message = LanguageMessageKey.ROOM_TYPE_ROOMS_REQUIRED) @NotBlank(
    message = LanguageMessageKey.ROOM_TYPE_ROOMS_REQUIRED
  ) String> rooms;

  @NotNull(message = LanguageMessageKey.ROOM_TYPE_RATE_REQUIRED)
  @Min(0)
  @Max(5)
  private float rate;

  @NotNull(message = LanguageMessageKey.ROOM_TYPE_STOCK_PRICE_REQUIRED)
  @Min(0)
  private float stockPrice;
}
