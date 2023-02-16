package com.chilleric.franchise_sys.repository.system_repository.navbar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "navbar")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Navbar {
  private ObjectId _id;
  private String name;
  private List<ContentNavbar> content;
}
