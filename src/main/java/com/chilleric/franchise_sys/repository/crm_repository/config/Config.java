package com.chilleric.franchise_sys.repository.crm_repository.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {
  private ObjectId _id;

  private ObjectId hotelId;
  private float billDeposit;
  private float VAT;
}
