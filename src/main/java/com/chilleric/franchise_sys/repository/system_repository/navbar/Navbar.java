package com.chilleric.franchise_sys.repository.system_repository.navbar;

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
  private List<ContentNavbar> content;
}
