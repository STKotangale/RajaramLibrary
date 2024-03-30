package com.raja.lib.payload.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequestDto {

	private String invoiceNo;
	private Date invoiceDate;
	private String partyName;
	private int billTotal;
	private int discountPercent;
	private int discountAmount;
	private int totalAfterDiscount;
	private int gstPercent;
	private int gstAmount;
	private int grandTotal;
	private List<PurchaseDetailDto> purchaseDetails;
}
