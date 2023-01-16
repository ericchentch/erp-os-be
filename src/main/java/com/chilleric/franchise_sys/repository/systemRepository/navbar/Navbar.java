package com.chilleric.franchise_sys.repository.systemRepository.navbar;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "navbar")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Navbar {
    private ObjectId _id;
    private String name;
    private List<ObjectId> userIds;
    private List<ObjectId> mainSidebar;
    private List<ObjectId> childrenSidebar;
}
