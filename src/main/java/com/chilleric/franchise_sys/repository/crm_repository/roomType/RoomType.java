package com.chilleric.franchise_sys.repository.crm_repository.roomType;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "room_types")
public class RoomType {
  private ObjectId _id;
  private ObjectId hotelId;
  private String name;
  private List<String> linkImages;
  private List<String> rooms;
  private float rate;
  private float stockPrice;
}
