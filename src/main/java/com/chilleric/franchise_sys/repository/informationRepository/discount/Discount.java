package com.chilleric.franchise_sys.repository.informationRepository.discount;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "discounts")
public class Discount {
    private ObjectId _id;

    private String name;
    private float discount;
    private String policy;
    private List<String> userIds;
}
