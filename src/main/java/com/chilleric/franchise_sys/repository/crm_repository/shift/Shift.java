package com.chilleric.franchise_sys.repository.crm_repository.shift;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shifts")
public class Shift {
  private ObjectId _id;
  private String shiftName;
  private Date startTime;
  private Date endTime;
}