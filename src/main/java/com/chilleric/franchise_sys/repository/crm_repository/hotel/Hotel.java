package com.chilleric.franchise_sys.repository.crm_repository.hotel;

import com.chilleric.franchise_sys.repository.common_entity.ClientInfomation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
  private ObjectId _id;

  private String description;
  private String name;
  private List<String> linkImages;
  private List<ClientInfomation> client;

  private float billDeposit;
  private float VAT;
  private float maxRefund;
  private int maxDaysRefund;

  private float maxWorkHours;
  private float maxShift;
  private ObjectId permissionId;
}
