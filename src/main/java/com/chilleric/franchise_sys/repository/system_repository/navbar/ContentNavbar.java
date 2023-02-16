package com.chilleric.franchise_sys.repository.system_repository.navbar;

import java.util.List;
import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentNavbar {
  private ObjectId mainItem;
  private List<ObjectId> childrenItem;
}
