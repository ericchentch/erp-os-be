package com.chilleric.franchise_sys.repository.crm_repository.calendar;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "calendars")
public class Calendar {
  private ObjectId _id;

  private ObjectId roomTypeId;
  private String position;
  private ObjectId billId;
  private Date checkoutTime;
  private Date checkinTime;
}
