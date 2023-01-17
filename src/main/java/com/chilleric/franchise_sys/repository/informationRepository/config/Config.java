package com.chilleric.franchise_sys.repository.informationRepository.config;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
