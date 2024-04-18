package com.raja.lib.invt.resposne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto {
	private Long purchaseId;
	private String invoiceNo;
	private String message;
	private int statusCode;
	private boolean success;
	private String ledgerName;

}
