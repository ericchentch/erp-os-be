package com.chilleric.franchise_sys.dto.roomType;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeResponse {
  private String id;
  private String hotelId;
  private String name;
  private List<String> linkImages;
  private List<String> rooms;
  private float rate;
  private float stockPrice;
}
