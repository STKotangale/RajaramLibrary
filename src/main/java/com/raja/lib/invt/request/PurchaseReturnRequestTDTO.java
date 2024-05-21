package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class PurchaseReturnRequestTDTO {

	private String invoiceNO;
	private String invoiceDate;
	private int LedgerId;
	
	
}
