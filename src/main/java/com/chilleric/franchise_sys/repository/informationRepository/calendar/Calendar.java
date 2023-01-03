package com.chilleric.franchise_sys.repository.informationRepository.calendar;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "calendars")
public class Calendar {
    private ObjectId _id;

    private int year;
    private int month;

    private ObjectId assigneeId;
    private List<Assignment> assignments;
}
