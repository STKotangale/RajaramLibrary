package com.raja.lib.payload.response;

import java.util.Date;
import java.util.List;

import com.raja.lib.payload.request.PurchaseDetailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPurchaseReponseDto {
	private Long purchaseId;
	private String invoiceNo;
	private Date invoiceDate;
	private String partyName;
	private double billTotal;
	private double discountPercent;
	private double discountAmount;
	private double totalAfterDiscount;
	private double gstPercent;
	private double gstAmount;
	private double grandTotal;
	private List<PurchaseDetailDto> purchaseDetails;
}
