package com.chilleric.franchise_sys.repository.informationRepository.varian;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "varians")
public class Varian {
    private ObjectId _id;

    private String name;
    private float price;
    private List<ObjectId> roomTypeId;
}
