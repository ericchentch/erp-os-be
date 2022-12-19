package com.chilleric.franchise_sys.repository.path;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "paths")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Path {
    private ObjectId _id;
    private String label;
    private String path;
}
