package com.chilleric.franchise_sys.repository.system_repository.code;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
  private ObjectId _id;
  private ObjectId userId;
  private TypeCode type;
  private String code;
  private Date expiredDate;
}
