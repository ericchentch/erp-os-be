package com.chilleric.franchise_sys.dto.hotel;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
	private String hotelId;
	private String name;
	private String description;
	private List<String> linkImages;
	private List<ClientRequest> client;

	private float billDeposit;
	private float VAT;
	private float maxRefund;
	private int maxDaysRefund;

	private float maxWorkHours;
	private float maxShift;
	private String permissionId;
}
