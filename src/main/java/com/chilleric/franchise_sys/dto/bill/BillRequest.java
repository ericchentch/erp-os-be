package com.chilleric.franchise_sys.dto.bill;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
	private String customerId;
	private List<DraftDetailRequest> listDrafDetailRequest;
}
