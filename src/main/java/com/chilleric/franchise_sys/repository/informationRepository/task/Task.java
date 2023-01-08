package com.chilleric.franchise_sys.repository.informationRepository.task;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    private ObjectId _id;

    private ObjectId employeeId;
    private ObjectId reviewerId;
    private ObjectId shiftId;

    private Date startTime;
    private String title;
    private String description;
    private TaskStatus status;
}
