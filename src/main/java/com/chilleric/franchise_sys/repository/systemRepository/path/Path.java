package com.chilleric.franchise_sys.repository.systemRepository.path;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
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
    private TypeAccount type;
    private List<ObjectId> userId;
    private String icon;
}
