package com.chilleric.franchise_sys.repository.crm_repository.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "services")
public class Service {
  private ObjectId _id;

  private String name;
  private float price;
}
