package com.raja.lib.invt.request;

import lombok.Data;

@Data
public class PurchaseReturnRequestDTO {
	private String invoiceNO;
	private String invoiceDate;
	private int ledgerId;
	private int bookdetailId;
	private int amount;
	private int billTotal;
	private int grandTotal;
	private int discount;
	private int totalAfterDiscount;

}
