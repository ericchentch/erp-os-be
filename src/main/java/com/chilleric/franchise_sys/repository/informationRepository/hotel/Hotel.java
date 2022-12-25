package com.chilleric.franchise_sys.repository.informationRepository.hotel;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import com.chilleric.franchise_sys.repository.common_entity.ClientInfomation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
	private ObjectId _id;

	private String description;
	private String name;
	private List<String> linkImages;
	private List<ClientInfomation> client;
}