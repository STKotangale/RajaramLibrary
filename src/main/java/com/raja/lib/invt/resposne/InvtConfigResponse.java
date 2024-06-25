package com.raja.lib.invt.resposne;

import lombok.Data;

@Data
public class InvtConfigResponse {
	private int srno;
	private Integer bookDays;
	private Double finePerDays;
	private Double monthlyFees;
}
