package com.chilleric.franchise_sys.repository.system_repository.navbar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentNavbar {
  private ObjectId mainItem;
  private List<ObjectId> childrenItem;
}
