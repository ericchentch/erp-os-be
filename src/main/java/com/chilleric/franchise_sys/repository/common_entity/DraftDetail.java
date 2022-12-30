package com.chilleric.franchise_sys.repository.common_entity;

import org.bson.types.ObjectId;
import com.chilleric.franchise_sys.repository.common_enum.TypeObjectBill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DraftDetail {
    private TypeObjectBill type;
    private ObjectId id;
    private int quantity;
}
