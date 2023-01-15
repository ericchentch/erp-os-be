package com.chilleric.franchise_sys.repository.informationRepository.serve;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "serve")
public class Serve {
    private ObjectId _id;

    private String name;
    private float price;
}
