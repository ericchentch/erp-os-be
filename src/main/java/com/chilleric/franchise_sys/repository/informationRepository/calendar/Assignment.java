package com.chilleric.franchise_sys.repository.informationRepository.calendar;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    private int day;
    private ObjectId shiftId;
}
