package com.chilleric.franchise_sys.repository.informationRepository.shift;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shifts")
public class Shift {
    private ObjectId _id;
    private String shiftName;
    private Date startDate;
    private Date endDate;
}
